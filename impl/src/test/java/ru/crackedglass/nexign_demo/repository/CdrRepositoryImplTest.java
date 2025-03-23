package ru.crackedglass.nexign_demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.jdbc.Sql;

import ru.crackedglass.nexign_demo.annotation.SqlClearDb;
import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.exception.cdr.CdrException;
import ru.crackedglass.nexign_demo.repository.impl.CdrRepositoryImpl;

@SqlClearDb
@SpringBootTest
public class CdrRepositoryImplTest {

    @Autowired
    CdrRepositoryImpl cdrRepository;

    @Sql("/sql/TestData.sql")
    @Test
    void shouldAddNewEntityWhenNotExists() {
        var entity = new CdrEntity(null,
                "02",
                new SubscriberEntity(1L, null),
                new SubscriberEntity(3L, null),
                Instant.now().truncatedTo(ChronoUnit.MICROS),
                Instant.now().plusSeconds(10).truncatedTo(ChronoUnit.MICROS));

        var actual = cdrRepository.insert(entity);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.callType()).isEqualTo(entity.callType());
        assertThat(actual.caller().id()).isEqualTo(entity.caller().id());
        assertThat(actual.receiver().id()).isEqualTo(entity.receiver().id());
        assertThat(actual.startTimestamp()).isEqualTo(entity.startTimestamp());
        assertThat(actual.endTimestamp()).isEqualTo(entity.endTimestamp());
    }

    @Sql("/sql/TestDataExplicit.sql")
    @Test
    void shouldThrowExceptionWhenExists() {
        var entity = new CdrEntity(100L,
                "INCOMING",
                new SubscriberEntity(1L, null),
                new SubscriberEntity(2L, null),
                Instant.now().truncatedTo(ChronoUnit.MICROS),
                Instant.now().plusSeconds(10).truncatedTo(ChronoUnit.MICROS));

        assertThatThrownBy(() -> cdrRepository.insert(entity))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Sql("/sql/TestData.sql")
    @Test
    void shouldUpdateWhenExists() {
        var entity = new CdrEntity(1L,
                "02",
                new SubscriberEntity(3L, null),
                new SubscriberEntity(2L, null),
                Instant.now().truncatedTo(ChronoUnit.MICROS),
                Instant.now().plusSeconds(10).truncatedTo(ChronoUnit.MICROS));

        var actual = cdrRepository.update(entity);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.callType()).isEqualTo(entity.callType());
        assertThat(actual.caller().id()).isEqualTo(entity.caller().id());
        assertThat(actual.receiver().id()).isEqualTo(entity.receiver().id());
        assertThat(actual.startTimestamp()).isEqualTo(entity.startTimestamp());
        assertThat(actual.endTimestamp()).isEqualTo(entity.endTimestamp());
    }

    @Sql("/sql/TestData.sql")
    @Test
    void shouldThrowExceptionWhenTryingToUpdateAndNotExists() {
        var entity = new CdrEntity(10L,
                "INCOMING",
                new SubscriberEntity(1L, null),
                new SubscriberEntity(2L, null),
                Instant.now().truncatedTo(ChronoUnit.MICROS),
                Instant.now().plusSeconds(10).truncatedTo(ChronoUnit.MICROS));

        assertThatThrownBy(() -> cdrRepository.update(entity))
                .isInstanceOf(CdrException.class);
    }

    @Sql("/sql/FindAllTest.sql")
    @Test
    void shouldReturnListOfEntities() {
        List<CdrEntity> expected = List.of(
                new CdrEntity(1L,
                        "02",
                        new SubscriberEntity(1L, "1"),
                        new SubscriberEntity(2L, "2"),
                        null, null),
                new CdrEntity(2L,
                        "01",
                        new SubscriberEntity(2L, "2"),
                        new SubscriberEntity(3L, "3"),
                        null, null));

        var actual = cdrRepository.findAll();

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("startTimestamp", "endTimestamp")
                .isEqualTo(expected);
    }
    // @Sql("/sql/TestData.sql")
    // @Test
    // void shouldUpdateEntityWhenItExists() {
    // var entity = new SubscriberEntity(1L, "123");

    // var actual = cdrRepository.update(entity);
    // assertThat(actual).isEqualTo(entity);
    // }

    // @Test
    // void shouldThrowExceptionWhenItNotExists() {
    // var entity = new SubscriberEntity(1242L, "unreal");

    // assertThatThrownBy(() -> cdrRepository.update(entity))
    // .isInstanceOf(SubscriberException.class);

    // }

}
