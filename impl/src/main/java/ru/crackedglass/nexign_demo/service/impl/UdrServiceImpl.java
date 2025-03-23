package ru.crackedglass.nexign_demo.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.dto.TotalTimeDto;
import ru.crackedglass.nexign_demo.dto.UdrDto;
import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.repository.CdrRepository;
import ru.crackedglass.nexign_demo.repository.SubscriberRepository;
import ru.crackedglass.nexign_demo.service.UdrService;

@Service
@RequiredArgsConstructor
public class UdrServiceImpl implements UdrService {

    private final CdrRepository cdrRepository;
    private final SubscriberRepository subscriberRepository;

    @Override
    public UdrDto getUdrByNumber(String number, Integer month) {

        List<CdrEntity> entities;

        if (month == null || month < 1 || month > 12)
            entities = cdrRepository.findByNumber(number);
        else
            entities = cdrRepository.findByNumberAndMonth(number, month);

        Duration incoming = calculateTotalTime(entities.stream(), "02");
        Duration outcoming = calculateTotalTime(entities.stream(), "01");

        return new UdrDto(number,
                new TotalTimeDto(format(incoming)),
                new TotalTimeDto(format(outcoming)));
    }

    @Override
    public List<UdrDto> getUdrsByMonth(Integer month) {
        List<CdrEntity> entities = cdrRepository.findByMonth(month);

        List<UdrDto> result = new ArrayList<>();
        List<String> numbers = subscriberRepository.findAll().stream().map(SubscriberEntity::number).toList();
        for (String number : numbers) {
            Stream<CdrEntity> filteredOutcoming = entities.stream()
                    .filter(entity -> entity.caller().number().equals(number));
            Duration outcoming = calculateTotalTime(filteredOutcoming, "01");

            Stream<CdrEntity> filteredIncoming = entities.stream()
                    .filter(entity -> entity.receiver().number().equals(number));
            Duration incoming = calculateTotalTime(filteredIncoming, "02");

            result.add(new UdrDto(number,
                    new TotalTimeDto(format(incoming)),
                    new TotalTimeDto(format(outcoming))));
        }
        return result;
    }

    private Duration calculateTotalTime(Stream<CdrEntity> entities, String callType) {
        return entities
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
