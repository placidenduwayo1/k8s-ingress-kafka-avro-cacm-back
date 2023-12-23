package fr.placide.cacmbsmsaddress.domain.usecase;

import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;

public class AddressValidationTools {
    private AddressValidationTools(){}
    public static void format(AddressDto dto){
        dto.setStreet(dto.getStreet().strip());
        dto.setCity(dto.getCity().strip());
        dto.setCountry(dto.getCountry().strip());
    }
    public static boolean isValid(AddressDto dto){
        return dto.getNum() >0
                && !dto.getStreet().isBlank()
                && dto.getPb()>100
                && !dto.getCity().isBlank()
                && !dto.getCountry().isBlank();
    }
}
