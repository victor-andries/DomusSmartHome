package com.victor.mongodb.repository;

import com.victor.mongodb.model.Dispozitiv;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.*;

public interface DispozitivRepository extends MongoRepository<Dispozitiv, String> {
    List<Dispozitiv> findByIdUtilizator(String idUtilizator);
}
