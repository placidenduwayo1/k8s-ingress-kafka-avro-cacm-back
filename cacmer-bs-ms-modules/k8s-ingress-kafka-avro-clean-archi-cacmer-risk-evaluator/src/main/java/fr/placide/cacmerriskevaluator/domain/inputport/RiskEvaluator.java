package fr.placide.cacmerriskevaluator;

public interface RiskEvaluator {
    public boolean evaluate(String mvtSens, double mvtAmount, String customerRisk, double balance, double overdraft);
}
