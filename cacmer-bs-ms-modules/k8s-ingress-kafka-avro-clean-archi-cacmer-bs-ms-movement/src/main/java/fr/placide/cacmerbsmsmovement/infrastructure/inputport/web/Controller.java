package fr.placide.cacmerbsmsmovement.infrastructure.inputport.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/bs-ms-mvt")
public class Controller {
    @Value("${welcome}")
    private String [] welcome;
    @GetMapping(value = "")
    public Map<String, Object> getWelcome(){
        return Map.of(welcome[0], welcome[1]);
    }
}
