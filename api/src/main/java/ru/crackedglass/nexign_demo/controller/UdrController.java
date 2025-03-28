package ru.crackedglass.nexign_demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import ru.crackedglass.nexign_demo.dto.UdrDto;

public interface UdrController {
    
    @GetMapping("/udrs/numbers/{number}")
    ResponseEntity<UdrDto> getByNumber(
        @PathVariable String number, 
        @RequestParam(required = false) Integer month);

    @GetMapping("/udrs/months/{month}")
    ResponseEntity<List<UdrDto>> getAllByMonth(
        @PathVariable @Min(1) @Max(12) Integer month
    );
}
