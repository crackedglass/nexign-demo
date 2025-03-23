package ru.crackedglass.nexign_demo.repository.impl;

import static ru.crackedglass.nexign_demo.entities.jooq.tables.Subscribers.SUBSCRIBERS;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.impl.DefaultRecordUnmapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.entities.jooq.tables.records.SubscribersRecord;
import ru.crackedglass.nexign_demo.exception.subcriber.SubscriberException;
import ru.crackedglass.nexign_demo.repository.SubscriberRepository;

@Repository
@Transactional
@RequiredArgsConstructor
public class SubscriberRepositoryImpl implements SubscriberRepository {

    private final DSLContext dsl;
    private final DefaultRecordUnmapper<SubscriberEntity, SubscribersRecord> unmapper = new DefaultRecordUnmapper<>(SubscriberEntity.class, SUBSCRIBERS.recordType(), null);
   
    @Override
    public SubscriberEntity insert(SubscriberEntity entity) throws DuplicateKeyException {
        return dsl.insertInto(SUBSCRIBERS)
            .set(unmapper.unmap(entity))
            .returning()
            .fetchOneInto(SubscriberEntity.class);
    }
    @Override
    public SubscriberEntity update(SubscriberEntity entity) throws SubscriberException {
        SubscriberEntity result = dsl.update(SUBSCRIBERS)
            .set(unmapper.unmap(entity))
            .where(SUBSCRIBERS.SUBSCRIBER_ID.eq(entity.id()))
            .returning()
            .fetchOneInto(SubscriberEntity.class);

        if (result == null)
            throw new SubscriberException("Can't update because subscriber doesn't exist");
        return result;
    }
    @Override
    public List<SubscriberEntity> findAll() {
        return dsl.select().from(SUBSCRIBERS)
            .fetchInto(SubscriberEntity.class);
    }

}
