package ru.crackedglass.nexign_demo.repository;

import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;

public interface SubscriberRepository {

    SubscriberEntity insert(SubscriberEntity entity);

    SubscriberEntity update(SubscriberEntity entity);

    
}
