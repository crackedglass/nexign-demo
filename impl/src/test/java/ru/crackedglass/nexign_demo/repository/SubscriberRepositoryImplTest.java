package ru.crackedglass.nexign_demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.jdbc.Sql;

import ru.crackedglass.nexign_demo.annotation.SqlClearDb;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.exception.subcriber.SubscriberException;
import ru.crackedglass.nexign_demo.repository.impl.SubscriberRepositoryImpl;

@SqlClearDb
@SpringBootTest
public class SubscriberRepositoryImplTest {

    @Autowired
    SubscriberRepositoryImpl subscriberRepository;

    @Test
    void shouldAddNewEntityWhenNotExists() {
        var entity = new SubscriberEntity(null, "test");

        var actual = subscriberRepository.insert(entity);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.number()).isEqualTo(entity.number());
    }

    @Sql("/sql/TestData.sql")
    @Test
    void shouldThrowExceptionWhenExists() {
        var entity = new SubscriberEntity(null, "12345623");

        assertThatThrownBy(() -> subscriberRepository.insert(entity))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Sql("/sql/TestDataExplicit.sql")
    @Test
    void shouldUpdateEntityWhenItExists() {
        var entity = new SubscriberEntity(100L, "123");

        var actual = subscriberRepository.update(entity);
        assertThat(actual).isEqualTo(entity);
    }

    @Test
    void shouldThrowExceptionWhenItNotExists() {
        var entity = new SubscriberEntity(1L, "unreal");

        assertThatThrownBy(() -> subscriberRepository.update(entity))
                .isInstanceOf(SubscriberException.class);

    }

    @Sql("/sql/FindAllTest.sql")
    @Test
    void shouldReturnAllSubscribersWhenDbNotEmpty(){
        var expected = List.of(
            new SubscriberEntity(1L, "1"),
            new SubscriberEntity(2L, "2"),
            new SubscriberEntity(3L, "3")
        );

        var actual = subscriberRepository.findAll();

        assertThat(actual).isEqualTo(expected);
    }
}
