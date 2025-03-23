package ru.crackedglass.nexign_demo.repository.impl;

import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.extract;
import static ru.crackedglass.nexign_demo.entities.jooq.tables.Cdrs.CDRS;
import static ru.crackedglass.nexign_demo.entities.jooq.tables.Subscribers.SUBSCRIBERS;

import java.time.Instant;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.DatePart;
import org.jooq.Record6;
import org.jooq.RecordMapper;
import org.jooq.RecordUnmapper;
import org.jooq.SelectOnConditionStep;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.entities.jooq.tables.Subscribers;
import ru.crackedglass.nexign_demo.entities.jooq.tables.records.CdrsRecord;
import ru.crackedglass.nexign_demo.exception.cdr.CdrException;
import ru.crackedglass.nexign_demo.repository.CdrRepository;

@Repository
@Transactional
public class CdrRepositoryImpl implements CdrRepository {

    private final DSLContext dsl;

    public CdrRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    private final Subscribers caller = SUBSCRIBERS.as("caller");
    private final Subscribers receiver = SUBSCRIBERS.as("receiver");

    @Override
    public CdrEntity insert(CdrEntity entity) throws DuplicateKeyException {
        return dsl.insertInto(CDRS)
                .set(unmapper.unmap(entity))
                .returning()
                .fetchOne(mapper);
    }

    @Override
    public CdrEntity update(CdrEntity entity) throws CdrException {
        CdrEntity result = dsl.update(CDRS)
                .set(unmapper.unmap(entity))
                .where(CDRS.CDR_ID.eq(entity.id()))
                .returning()
                .fetchOne(mapper);

        if (result == null)
            throw new CdrException("Can't update because cdr doesn't exist");
        return result;
    }

    @Override
    public List<CdrEntity> findAll() {
        return find()
                .fetchInto(CdrEntity.class);
    }

    @Override
    public List<CdrEntity> findByNumber(String number) {
        Condition callerCondition = caller.NUMBER.eq(number);
        Condition receiverCondition = receiver.NUMBER.eq(number);

        return find()
                .where(callerCondition)
                .or(receiverCondition)
                .fetchInto(CdrEntity.class);
    }

    @Override
    public List<CdrEntity> findByMonth(Integer month) {
        Condition monthCondition = extract(CDRS.START_TIMESTAMP, DatePart.MONTH).eq(month);

        return find()
            .where(monthCondition)
            .fetchInto(CdrEntity.class);
    }

    @Override
    public List<CdrEntity> findByNumberAndMonth(String number, Integer month){
        Condition callerCondition = caller.NUMBER.eq(number);
        Condition receiverCondition = receiver.NUMBER.eq(number);
        Condition monthCondition = extract(CDRS.START_TIMESTAMP, DatePart.MONTH).eq(month);

        return find()
            .where(callerCondition.or(receiverCondition))
            .and(monthCondition)
            .fetchInto(CdrEntity.class);
    }

    private SelectOnConditionStep<Record6<Long, String, SubscriberEntity, SubscriberEntity, Instant, Instant>> find() {
        return dsl.select(
                CDRS.CDR_ID,
                CDRS.CALL_TYPE,
                row(CDRS.CALLER_SUBSCRIBER_ID, caller.NUMBER).mapping(SubscriberEntity::new),
                row(CDRS.RECEIVER_SUBSCRIBER_ID, receiver.NUMBER).mapping(SubscriberEntity::new),
                CDRS.START_TIMESTAMP,
                CDRS.END_TIMESTAMP)
                .from(CDRS)
                .join(caller).on(CDRS.CALLER_SUBSCRIBER_ID.eq(caller.SUBSCRIBER_ID))
                .join(receiver).on(CDRS.RECEIVER_SUBSCRIBER_ID.eq(receiver.SUBSCRIBER_ID));
    }

    RecordUnmapper<CdrEntity, CdrsRecord> unmapper = entity -> new CdrsRecord(
            entity.id(),
            entity.callType(),
            entity.caller().id(),
            entity.receiver().id(),
            entity.startTimestamp(),
            entity.endTimestamp());

    RecordMapper<CdrsRecord, CdrEntity> mapper = record -> new CdrEntity(
            record.getCdrId(),
            record.getCallType(),
            new SubscriberEntity(record.getCallerSubscriberId(), null),
            new SubscriberEntity(record.getReceiverSubscriberId(), null),
            record.getStartTimestamp(),
            record.getEndTimestamp());

  

}
