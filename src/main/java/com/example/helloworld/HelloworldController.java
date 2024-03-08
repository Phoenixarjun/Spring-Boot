package com.example.helloworld;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {
    @GetMapping("/")
    public String getWelcomeText(){
        return  "Naresh";
    }
} 

https://github.com/Phoenixarjun/Spring-Boot.git