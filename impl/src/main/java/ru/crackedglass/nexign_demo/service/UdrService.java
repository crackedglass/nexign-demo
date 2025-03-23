package ru.crackedglass.nexign_demo.service;

import java.util.List;

import ru.crackedglass.nexign_demo.dto.UdrDto;

public interface UdrService {
    
    UdrDto getUdrByNumber(String number, int month);
} 
