package ru.crackedglass.nexign_demo.service.impl;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.entities.domain.CdrEntity;
import ru.crackedglass.nexign_demo.repository.CdrRepository;
import ru.crackedglass.nexign_demo.service.CdrService;

@Service
@RequiredArgsConstructor
public class CdrServiceImpl implements CdrService{

    private final CdrRepository cdrRepository;

    @Override
    public CdrEntity upsert(CdrEntity entity) {
        CdrEntity result;
        try {
            result = cdrRepository.insert(entity);
        } catch(DuplicateKeyException e){
            result = cdrRepository.update(entity);
        }
        return result;
    }

    @Override
    public List<CdrEntity> getAll() {
        return cdrRepository.findAll();
    }
    
}
