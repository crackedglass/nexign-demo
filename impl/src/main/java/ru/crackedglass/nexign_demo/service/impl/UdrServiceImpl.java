package ru.crackedglass.nexign_demo.service.impl;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.dto.TotalTimeDto;
import ru.crackedglass.nexign_demo.dto.UdrDto;
import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.repository.CdrRepository;
import ru.crackedglass.nexign_demo.service.UdrService;

@Service
@RequiredArgsConstructor
public class UdrServiceImpl implements UdrService {

    private final CdrRepository cdrRepository;

    @Override
    public UdrDto getUdrByNumber(String number, int month) {
        List<CdrEntity> entities = cdrRepository.findByNumber(number);

        Duration incoming = entities.stream()
                .filter(entity -> entity.callType() == "02")
                .map(entity -> Duration.between(entity.startTimestamp(), entity.endTimestamp()))
                .reduce((a, b) -> a.plus(b)).orElse(Duration.ZERO);

        Duration outcoming = entities.stream()
                .filter(entity -> entity.callType() == "01")
                .map(entity -> Duration.between(entity.startTimestamp(), entity.endTimestamp()))
                .reduce((a, b) -> a.plus(b)).orElse(Duration.ZERO);

        return new UdrDto(number,
                new TotalTimeDto(format(incoming)),
                new TotalTimeDto(format(outcoming)));
    }

    private String format(Duration duration){
        long HH = duration.toHours();
        long MM = duration.toMinutesPart();
        long SS = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }
}
