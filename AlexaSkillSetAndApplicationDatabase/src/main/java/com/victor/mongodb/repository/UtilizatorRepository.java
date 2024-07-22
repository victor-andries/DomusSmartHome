package com.victor.mongodb.repository;

import com.victor.mongodb.model.Utilizator;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UtilizatorRepository extends MongoRepository<Utilizator, String> {
    Utilizator findByEmail(String email);
    Optional<Utilizator> findByToken(String token);
}
