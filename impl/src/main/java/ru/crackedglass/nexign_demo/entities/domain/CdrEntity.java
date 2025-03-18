package ru.crackedglass.nexign_demo.entities.domain;

import java.time.Instant;

public record CdrEntity(
        Long id,
        String callType,
        SubscriberEntity caller,
        SubscriberEntity receiver,
        Instant startTimestamp,
        Instant endTimestamp) {
}
