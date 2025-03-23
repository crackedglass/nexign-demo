package ru.crackedglass.nexign_demo.service;

import java.util.List;

import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;

public interface SubscriberService {
    
    SubscriberEntity upsert(SubscriberEntity entity);

    List<SubscriberEntity> getAll();
}
