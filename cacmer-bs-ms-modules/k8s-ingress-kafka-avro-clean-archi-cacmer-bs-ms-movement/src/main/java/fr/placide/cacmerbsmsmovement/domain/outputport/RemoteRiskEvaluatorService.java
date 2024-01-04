package fr.placide.cacmerbsmsmovement.domain.outputport;

public interface RemoteRiskEvaluatorService {
    double getRemoteRiskEvaluation(String accountId, String mvtSens, double mvtAmount);
}
