package ru.crackedglass.nexign_demo.repository;

import static org.assertj.core.api.Assertions.*;

import org.jooq.exception.DataAccessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.jdbc.Sql;

import ru.crackedglass.nexign_demo.annotation.SqlClearDb;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.repository.impl.SubscriberRepositoryImpl;

@SqlClearDb
@SpringBootTest
public class SubscriberRepositoryImplTest {

    @Autowired
    SubscriberRepositoryImpl subscriberRepository;

    @Test
    void shouldReturnNewEntityWhenNotExists() {
        var entity = new SubscriberEntity(null,"test");

        var actual = subscriberRepository.insert(entity);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.number()).isEqualTo(entity.number());
    }

    @Sql("/sql/TestData.sql")
    @Test
    void shouldThrowExceptionWhenExists(){
        var entity = new SubscriberEntity(null, "12345623");

        assertThatThrownBy(() -> subscriberRepository.insert(entity))
            .isInstanceOf(DuplicateKeyException.class);
    }

}
