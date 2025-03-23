package ru.crackedglass.nexign_demo.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.service.SubscriberService;

@Slf4j
@ConditionalOnProperty(prefix = "generator", name = "subscribers.enabled")
@Component
@RequiredArgsConstructor
public class SubscriberGenerator implements CommandLineRunner {

    private final SubscriberService subscriberService;

    @Value("${generator.subscribers.amount:10}")
    private String amount;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        log.info("Creation {} subscribers", amount);

        for (Long i = 1L; i <= Long.parseLong(amount); i++) {
            subscriberService.upsert(
                    new SubscriberEntity(null, 
                        faker.phoneNumber().cellPhone().replaceAll("[-(),. ]",""))
                    );
        }

        log.debug("All subscribers: {}", subscriberService.getAll());
    }

}
