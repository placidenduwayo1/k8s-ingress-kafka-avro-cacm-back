package fr.placide.gatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class GatewayController {
    @Value("${message}")
    private String[] msg;
    @GetMapping(value = "")
    public Map<String, Object> get(){
        return Map.of(msg[0], msg[1]);
    }
}
