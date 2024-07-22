package com.victor.mongodb.service;

import com.victor.mongodb.model.RegulaAutomatizare;
import com.victor.mongodb.repository.RegulaAutomatizareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegulaAutomatizareService {
    private final RegulaAutomatizareRepository regulaAutomatizareRepository;

    @Autowired
    public RegulaAutomatizareService(RegulaAutomatizareRepository regulaAutomatizareRepository) {
        this.regulaAutomatizareRepository = regulaAutomatizareRepository;
    }

    public List<RegulaAutomatizare> getAllReguli() {
        return regulaAutomatizareRepository.findAll();
    }

    public Optional<RegulaAutomatizare> getRegulaById(String id) {
        return regulaAutomatizareRepository.findById(id);
    }

    public RegulaAutomatizare createRegula(RegulaAutomatizare regula) {
        return regulaAutomatizareRepository.save(regula);
    }

    public void deleteRegula(String id) {
        regulaAutomatizareRepository.deleteById(id);
    }
}
