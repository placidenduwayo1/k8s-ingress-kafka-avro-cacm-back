package fr.placide.cacmerriskevaluator.domain.inputport;

public interface RiskEvaluator {
    double evaluate(String mvtSens, double mvtAmount, String customerRisk, double balance, double overdraft);
}
