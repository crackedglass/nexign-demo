package ru.crackedglass.nexign_demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.dto.UdrDto;
import ru.crackedglass.nexign_demo.service.UdrService;

@RestController
@RequiredArgsConstructor
public class UdrControllerImpl implements UdrController{

    private final UdrService udrService;

    @Override
    public ResponseEntity<UdrDto> getByNumber(String number, Integer month) {
        return ResponseEntity.ok(udrService.getUdrByNumber(number, month));
    }

    @Validated
    @Override
    public ResponseEntity<List<UdrDto>> getAllByMonth(@Min(1) @Max(12) Integer month) {
        return ResponseEntity.ok(udrService.getUdrsByMonth(month));
    }
    
}
