package ru.crackedglass.nexign_demo.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.dto.TotalTimeDto;
import ru.crackedglass.nexign_demo.dto.UdrDto;
import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.repository.CdrRepository;
import ru.crackedglass.nexign_demo.repository.SubscriberRepository;
import ru.crackedglass.nexign_demo.service.UdrService;

/**
 * Класс формирования UDR отчетов
 */
@Service
@RequiredArgsConstructor
public class UdrServiceImpl implements UdrService {

    private final CdrRepository cdrRepository;
    private final SubscriberRepository subscriberRepository;


    /**
     * Метод получения UDR по номеру и месяцу
     * Опирается на использование sql-запросов с join'ми для максимизации использования встроенных функций бд.
     * @param number - номер абонента
     * @param month - номер месяца, если null или не лежит в отрезке [1,12], игнорируется
     */
    @Override
    public UdrDto getUdrByNumber(String number, Integer month) {

        List<CdrEntity> entities;

        if (month == null || month < 1 || month > 12)
            entities = cdrRepository.findByNumber(number);
        else
            entities = cdrRepository.findByNumberAndMonth(number, month);

        Duration incoming = calculateTotalTime(entities, "02");
        Duration outcoming = calculateTotalTime(entities, "01");

        return new UdrDto(number,
                new TotalTimeDto(format(incoming)),
                new TotalTimeDto(format(outcoming)));
    }

    /**
     * Метод получения всех отчетов UDR по месяцу
     * Опирается на использование sql-запросов с join'ми для максимизации использования встроенных функций бд.
     * 
     * @param month - номер месяца, валидируется на уровне контроллера
     */
    @Override
    public List<UdrDto> getUdrsByMonth(Integer month) {

        List<UdrDto> result = new ArrayList<>();
        List<String> numbers = subscriberRepository.findAll().stream().map(SubscriberEntity::number).toList();
        for (String number : numbers) {
            List<CdrEntity> entities = cdrRepository.findByNumberAndMonth(number, month);
            Duration outcoming = calculateTotalTime(entities, "01");

            Duration incoming = calculateTotalTime(entities, "02");

            result.add(new UdrDto(number,
                    new TotalTimeDto(format(incoming)),
                    new TotalTimeDto(format(outcoming))));
        }
        return result;
    }

    private Duration calculateTotalTime(List<CdrEntity> entities, String callType) {
        return entities.stream()
                .filter(entity -> entity.callType().equals(callType))
                .map(entity -> Duration.between(entity.startTimestamp(), entity.endTimestamp()))
                .reduce((a, b) -> a.plus(b)).orElse(Duration.ZERO);
    }

    private String format(Duration duration) {
        long HH = duration.toHours();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }

}
