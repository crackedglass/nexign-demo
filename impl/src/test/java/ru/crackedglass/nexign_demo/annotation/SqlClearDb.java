package ru.crackedglass.nexign_demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Sql(value = "classpath:sql/ClearDb.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = "classpath:sql/ClearDb.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public @interface SqlClearDb {
    
}
