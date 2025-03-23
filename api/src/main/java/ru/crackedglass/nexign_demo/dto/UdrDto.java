package ru.crackedglass.nexign_demo.dto;

public record UdrDto(
    String msisdn,
    TotalTimeDto incomingCall,
    TotalTimeDto outcomingCall
) {
    
}
