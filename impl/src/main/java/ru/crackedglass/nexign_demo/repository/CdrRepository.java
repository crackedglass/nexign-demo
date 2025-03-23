package ru.crackedglass.nexign_demo.repository;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.exception.cdr.CdrException;

public interface CdrRepository {
    
    CdrEntity insert(CdrEntity entity) throws DuplicateKeyException;

    CdrEntity update(CdrEntity entity) throws CdrException;

    List<CdrEntity> findAll();

    List<CdrEntity> findByNumber(String number);
}
