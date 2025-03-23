package ru.crackedglass.nexign_demo.service;

import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;

public interface CdrService {

    CdrEntity upsert(CdrEntity entity);
    
}
