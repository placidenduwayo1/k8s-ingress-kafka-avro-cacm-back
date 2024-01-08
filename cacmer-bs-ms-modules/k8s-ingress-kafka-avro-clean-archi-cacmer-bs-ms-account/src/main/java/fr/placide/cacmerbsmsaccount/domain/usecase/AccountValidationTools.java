package fr.placide.cacmerbsmsaccount.domain.usecase;

import fr.placide.cacmerbsmsaccount.domain.beans.Type;
import fr.placide.cacmerbsmsaccount.infrastructure.outputport.models.AccountDto;

public class AccountValidationTools {
    private AccountValidationTools() {}
    public static void format(AccountDto dto){
        dto.setType(dto.getType().strip());
        dto.setCustomerId(dto.getCustomerId().strip());
    }
    public static boolean isValidAccount(AccountDto dto){
        return !dto.getType().isBlank()
                && !dto.getCustomerId().isBlank();
    }
    public static boolean isValidType(String type){
        boolean exists=false;
        for(Type it :Type.values()){
            if(it.getAccountType().equals(type)){
                exists=true;
                break;
            }
        }
        return exists;
    }
    public static boolean isValidOverDraft(double overdraft){
        return overdraft>=0;
    }
    public static boolean isValidRemoteCustomerStatus(String status){
        return status.equals("active");
    }
}
