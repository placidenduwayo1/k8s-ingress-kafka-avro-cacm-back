package fr.placide.cacmerriskevaluator;

import org.springframework.stereotype.Service;

@Service
public class RiskEvaluatorImpl implements RiskEvaluator{
    @Override
    public boolean evaluate(String mvtSens, double mvtAmount, String customerRisk, double balance, double overdraft) {
        if(mvtSens.equals("sell")){
            balance+=mvtAmount;
        }
        else if (mvtSens.equals("buy")){
            if(customerRisk.equals("low")){
                balance -= overdraft*1+0.1;
            } else if (customerRisk.equals("high")) {
                balance+=overdraft*1.3;
            }
        }
        return balance-50>=mvtAmount;
    }
}