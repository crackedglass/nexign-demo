package ru.crackedglass.nexign_demo.generator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(prefix = "generator", name = "cdrs.enabled")
@Component
public class CdrGenerator implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        

        
    }

}
