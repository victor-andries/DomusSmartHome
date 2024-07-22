package com.victor.mongodb.repository;

import com.victor.mongodb.model.Actiune;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActiuneRepository extends MongoRepository<Actiune, String> {
}
