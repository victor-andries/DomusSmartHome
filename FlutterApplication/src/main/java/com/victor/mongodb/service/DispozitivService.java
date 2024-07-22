package com.victor.mongodb.service;

import com.victor.mongodb.model.Dispozitiv;
import com.victor.mongodb.repository.DispozitivRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispozitivService {
    private final DispozitivRepository dispozitivRepository;

    @Autowired
    public DispozitivService(DispozitivRepository dispozitivRepository) {
        this.dispozitivRepository = dispozitivRepository;
    }

    public List<Dispozitiv> getAllDispozitive() {
        return dispozitivRepository.findAll();
    }

    public Optional<Dispozitiv> getDispozitivById(String id) {
        return dispozitivRepository.findById(id);
    }

    public Dispozitiv createDispozitiv(Dispozitiv dispozitiv) {
        return dispozitivRepository.save(dispozitiv);
    }

    public void deleteDispozitiv(String id) {
        dispozitivRepository.deleteById(id);
    }
}
