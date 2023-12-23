package fr.placide.cacmbsmscustomer.domain.usecase;

import fr.placide.cacmbsmscustomer.domain.beans.Risk;
import fr.placide.cacmbsmscustomer.domain.beans.Status;
import fr.placide.cacmbsmscustomer.domain.exceptions.ExceptionMsg;
import fr.placide.cacmbsmscustomer.infrastructure.outputport.models.CustomerDto;

public class CustomerValidationTools {
    private CustomerValidationTools() {
    }

    public static void format(CustomerDto customerDto) {
        customerDto.setFirstname(customerDto.getFirstname().strip());
        customerDto.setLastname(customerDto.getLastname().strip());
        customerDto.setRisk(customerDto.getRisk().strip());
        customerDto.setStatus(customerDto.getStatus().strip());
        customerDto.setAddressId(customerDto.getAddressId().strip());
    }

    public static boolean isValid(CustomerDto customerDto) {
        return !customerDto.getFirstname().isBlank()
                && !customerDto.getLastname().isBlank()
                && !customerDto.getRisk().isBlank()
                && !customerDto.getStatus().isBlank()
                && !customerDto.getAddressId().isBlank();
    }
    public static boolean isValidRisk(String risk){
        boolean exists = false;
        for(Risk r:Risk.values()){
           if(risk.equals(r.getTheRisk())){
               exists=true;
               break;
           }
        }
        return exists;
    }
    public static boolean isValidStatus(String status){
        boolean exists = false;
        for(Status s: Status.values()){
            if(status.equals(s.getTheStatus())){
                exists=true;
                break;
            }
        }
        return exists;
    }
    public static boolean unreachableRemoteAddress(String unreachable){
       return unreachable.strip().equals(ExceptionMsg.REMOTE_ADDRESS_API.getMsg());
    }
}
