package fr.placide.cacmbsmsaddress.infrastructure.inputport.web;

import fr.placide.cacmbsmsaddress.domain.beans.Address;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.AddressAlreadyExistsException;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.AddressFieldsInvalidException;
import fr.placide.cacmbsmsaddress.domain.exceptions.business_exc.AddressNotFoundException;
import fr.placide.cacmbsmsaddress.domain.inputport.AddressInputService;
import fr.placide.cacmbsmsaddress.infrastructure.outputport.models.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/bs-ms-address")
public class AddressController {
    private final AddressInputService addressInputService;
    @Value("${msg}")
    private String [] msg;
    @GetMapping(value = "")
    public Map<String, Object> getWelcome() {
        return Map.of(msg[0],msg[1]);
    }

    @GetMapping(value = "/addresses")
    public List<Address> gets() {
        return addressInputService.getAllAddresses();
    }

    @PostMapping(value = "/addresses")
    public Address create(@RequestBody AddressDto dto) throws AddressAlreadyExistsException, AddressFieldsInvalidException {
        return addressInputService.createAddress(dto);
    }

    @GetMapping(value = "/addresses/id/{id}")
    public Address get(@PathVariable(name = "id") String id) throws AddressNotFoundException {
        return addressInputService.getAddressById(id);
    }

    @GetMapping(value = "/addresses/city/{city}")
    public List<Address> gets(@PathVariable(name = "city") String city) throws AddressNotFoundException {
        return addressInputService.getAddressesByCity(city);
    }
}
