package fr.placide.cacmerriskevaluator.domain.usecase;

import fr.placide.cacmerriskevaluator.domain.beans.Account;
import fr.placide.cacmerriskevaluator.domain.beans.Customer;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.outputport.RemoteObjectsGetter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class InputPortRiskEvaluatorImpl implements InputPortRiskEvaluator {
    private final RemoteObjectsGetter remoteObjectsGetter;

    public InputPortRiskEvaluatorImpl(RemoteObjectsGetter remoteObjectsGetter) {
        this.remoteObjectsGetter = remoteObjectsGetter;
    }

    @Override
    public double evaluate(String accountId, String movementSens, double movementAmount) throws RemoteAccountApiUnreachableException,
            RemoteCustomerApiUnreachableException {
        Account account = remoteObjectsGetter.getRemoteAccountById(accountId);
        if(account==null){
            throw new RemoteAccountApiUnreachableException();
        }
        Customer customer = remoteObjectsGetter.getRemoteCustomerById(account.getCustomerId());
        if(customer==null){
            throw new RemoteCustomerApiUnreachableException();
        }
        double balance = account.getBalance();
        if(movementSens.equals("sell")){
            balance+=movementAmount;
        }
        else if (movementSens.equals("buy")){
            if(customer.getRisk().equals("low")){
                balance-=account.getOverdraft()*1.1;
            } else if (customer.getRisk().equals("high")) {
                balance+=account.getOverdraft()*1.3;
            }
        }

        return balance;
    }
}