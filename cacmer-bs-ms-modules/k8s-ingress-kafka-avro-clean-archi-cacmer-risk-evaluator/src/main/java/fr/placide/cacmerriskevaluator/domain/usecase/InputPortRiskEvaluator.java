package fr.placide.cacmerriskevaluator.domain.usecase;

import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteAccountApiUnreachableException;
import fr.placide.cacmerriskevaluator.domain.exceptions.RemoteCustomerApiUnreachableException;

public sealed interface InputPortRiskEvaluator permits InputPortRiskEvaluatorImpl {
    double evaluate(String accountId, String movementSens,double movementAmount) throws RemoteAccountApiUnreachableException, RemoteCustomerApiUnreachableException;
}
