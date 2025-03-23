package ru.crackedglass.nexign_demo.repository;

import java.util.List;

import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;

public interface SubscriberRepository {

    SubscriberEntity insert(SubscriberEntity entity);

    SubscriberEntity update(SubscriberEntity entity);

    List<SubscriberEntity> findAll();
}
