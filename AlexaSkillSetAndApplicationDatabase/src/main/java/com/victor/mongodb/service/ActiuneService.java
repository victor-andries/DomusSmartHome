package com.victor.mongodb.service;

import com.victor.mongodb.model.Actiune;
import com.victor.mongodb.repository.ActiuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActiuneService {
    private final ActiuneRepository actiuneRepository;

    @Autowired
    public ActiuneService(ActiuneRepository actiuneRepository) {
        this.actiuneRepository = actiuneRepository;
    }

    public List<Actiune> getAllActiuni() {
        return actiuneRepository.findAll();
    }

    public Optional<Actiune> getActiuneById(String id) {
        return actiuneRepository.findById(id);
    }

    public Actiune createActiune(Actiune actiune) {
        return actiuneRepository.save(actiune);
    }

    public void deleteActiune(String id) {
        actiuneRepository.deleteById(id);
    }
}
