package ru.crackedglass.nexign_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.crackedglass.nexign_demo.dto.UdrDto;

public interface UdrController {
    
    @GetMapping("/udrs/numbers/{number}")
    ResponseEntity<UdrDto> getByNumber(
        @PathVariable String number, 
        @RequestParam(required = false) int month);
}
