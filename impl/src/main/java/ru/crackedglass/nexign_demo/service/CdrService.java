package ru.crackedglass.nexign_demo.service;

import java.util.List;

import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;

public interface CdrService {

    CdrEntity upsert(CdrEntity entity);

    List<CdrEntity> getAll();
    
}
