package fr.placide.cacmerriskevaluator.domain.usecase;

import fr.placide.cacmerriskevaluator.domain.inputport.RiskEvaluator;
public class RiskEvaluatorImpl implements RiskEvaluator {
    @Override
    public double evaluate(String mvtSens, double mvtAmount, String customerRisk, double balance, double overdraft) {
        if(mvtSens.equals("sell")){
            balance+=mvtAmount;
        }
        else if (mvtSens.equals("buy")){
            if(customerRisk.equals("low")){
                balance -= overdraft*1.1;
            } else if (customerRisk.equals("high")) {
                balance+=overdraft*1.3;
            }
        }
        return balance;
    }
}