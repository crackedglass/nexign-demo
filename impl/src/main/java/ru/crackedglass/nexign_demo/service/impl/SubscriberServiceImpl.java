package ru.crackedglass.nexign_demo.service.impl;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.crackedglass.nexign_demo.entities.domain.SubscriberEntity;
import ru.crackedglass.nexign_demo.repository.SubscriberRepository;
import ru.crackedglass.nexign_demo.service.SubscriberService;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Override
    public SubscriberEntity upsert(SubscriberEntity entity) {
        SubscriberEntity result;
        try {
            result = subscriberRepository.insert(entity);
        } catch(DuplicateKeyException e){
            result = subscriberRepository.update(entity);
        }
        return result;
    }

    @Override
    public List<SubscriberEntity> getAll() {
        return subscriberRepository.findAll();
    }
    
}
