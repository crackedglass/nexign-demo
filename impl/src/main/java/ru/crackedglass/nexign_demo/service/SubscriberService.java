package ru.crackedglass.nexign_demo.service;

import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;

public interface SubscriberService {
    
    SubscriberEntity upsert(SubscriberEntity entity);
}
