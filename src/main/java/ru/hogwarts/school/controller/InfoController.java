package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @Value("${port}")
    private Integer localPort;

    @GetMapping("/get-port")
    public Integer getLocalPort() {
        return localPort;
    }
}
