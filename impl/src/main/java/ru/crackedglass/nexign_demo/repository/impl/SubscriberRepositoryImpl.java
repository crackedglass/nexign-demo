package ru.crackedglass.nexign_demo.repository.impl;

import static ru.crackedglass.nexign_demo.entities.jooq.tables.Subscribers.SUBSCRIBERS;

import org.jooq.DSLContext;
import org.jooq.impl.DefaultRecordUnmapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.entities.jooq.tables.records.SubscribersRecord;
import ru.crackedglass.nexign_demo.repository.SubscriberRepository;

@Repository
@Transactional
@RequiredArgsConstructor
public class SubscriberRepositoryImpl implements SubscriberRepository {

    private final DSLContext dsl;
    private final DefaultRecordUnmapper<SubscriberEntity, SubscribersRecord> unmapper = new DefaultRecordUnmapper<>(SubscriberEntity.class, SUBSCRIBERS.recordType(), null);
    @Override
    public SubscriberEntity insert(SubscriberEntity entity) {
        return dsl.insertInto(SUBSCRIBERS)
            .set(unmapper.unmap(entity))
            .returning()
            .fetchOneInto(SubscriberEntity.class);
    }
    @Override
    public SubscriberEntity update(SubscriberEntity entity) {
        return dsl.update(SUBSCRIBERS)
            .set(unmapper.unmap(entity))
            .returning()
            .fetchOneInto(SubscriberEntity.class);
    }

}
