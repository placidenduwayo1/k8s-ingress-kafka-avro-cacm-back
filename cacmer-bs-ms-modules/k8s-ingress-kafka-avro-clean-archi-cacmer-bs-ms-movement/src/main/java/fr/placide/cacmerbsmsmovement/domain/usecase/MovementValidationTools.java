package fr.placide.cacmerbsmsmovement.domain.usecase;

import fr.placide.cacmerbsmsmovement.domain.beans.Sens;
import fr.placide.cacmerbsmsmovement.infrastructure.outputport.models.MovementDto;

public class MovementValidationTools {
    private MovementValidationTools() {
    }
    public static void format(MovementDto dto){
        dto.setSens(dto.getSens().strip());
        dto.setAccountId(dto.getAccountId().strip());
        dto.setCustomerId(dto.getCustomerId().strip());
    }
    public static boolean isValidMvt(MovementDto dto){
        return !dto.getSens().isBlank()
                && !dto.getAccountId().isBlank()
                && ! dto.getCustomerId().isBlank();
    }
    public static boolean isValidSens(String sens){
        boolean exists = false;
        for(Sens s: Sens.values()){
            if(s.getMvtSens().equals(sens)){
                exists = true;
                break;
            }
        }
        return exists;
    }
}
