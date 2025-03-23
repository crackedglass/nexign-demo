package ru.crackedglass.nexign_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.dto.UdrDto;
import ru.crackedglass.nexign_demo.service.UdrService;

@RestController
@RequiredArgsConstructor
public class UdrControllerImpl implements UdrController{

    private final UdrService udrService;

    @Override
    public ResponseEntity<UdrDto> getByNumber(String number, int month) {
        return ResponseEntity.ok(udrService.getUdrByNumber(number, month));
    }
    
}
