package ru.crackedglass.nexign_demo.generator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.service.CdrService;
import ru.crackedglass.nexign_demo.service.SubscriberService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CdrGenerator implements CommandLineRunner {

    private final SubscriberService subscriberService;
    private final SubscriberGenerator subscriberGenerator;
    private final CdrService cdrService;

    @Value("${generator.cdrs.amount:1000}")
    private String amount;
    @Value("${generator.cdrs.start:2025-01-01T00:00:00.00Z}")
    private String start;

    public static Instant randomBetween(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating {} cdrs from {}", amount, start);
        subscriberGenerator.generate();

        var subscribers = subscriberService.getAll();
        int size = subscribers.size();
        log.info("Amount of subscribers: {}", size);
        Instant generateFrom = Instant.parse(start);

        for (long i = 0; i < Long.parseLong(amount); i++) {
            int randomCallerIndex, randomReceiverIndex;
            do {
                randomCallerIndex = ThreadLocalRandom.current().nextInt(0, size);
                randomReceiverIndex = ThreadLocalRandom.current().nextInt(0, size);
            } while (randomCallerIndex == randomReceiverIndex);

            Instant startTimestamp = randomBetween(generateFrom,
                    generateFrom.plus(365, ChronoUnit.DAYS));
            Instant endTimestamp = startTimestamp.plusMillis(
                    ThreadLocalRandom.current().nextLong(1, 3600000));

            boolean isIncoming = ThreadLocalRandom.current().nextBoolean();

            CdrEntity entity = new CdrEntity(
                    null,
                    isIncoming ? "02" : "01",
                    subscribers.get(randomCallerIndex),
                    subscribers.get(randomReceiverIndex),
                    startTimestamp,
                    endTimestamp);

            cdrService.upsert(entity);
            if(i % 100 == 0)
                log.info("{} created", i);
        }
        
        log.info("Cdr generation completed");
    }

}
