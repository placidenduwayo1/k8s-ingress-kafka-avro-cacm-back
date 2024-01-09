package fr.placide.cacmerriskevaluator.domain.inputport;

import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;

public interface InputPortRiskEvaluator {
    double evaluate(String accountId, String movementSens,double movementAmount) throws RemoteAccountApiUnreachableException, RemoteCustomerApiUnreachableException;
}
