package fr.placide.msconfigservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigServerController {
    @Value("${message}")
    private String[] msg;
    @GetMapping(value = "")
    public Map<String, Object> welcome(){
        return Map.of(msg[0], msg[1]);
    }
}
