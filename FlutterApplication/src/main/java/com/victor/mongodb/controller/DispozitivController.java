package com.victor.mongodb.controller;

import com.victor.mongodb.model.Dispozitiv;
import com.victor.mongodb.service.DispozitivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dispozitive")
public class DispozitivController {
    private final DispozitivService dispozitivService;

    @Autowired
    public DispozitivController(DispozitivService dispozitivService) {
        this.dispozitivService = dispozitivService;
    }

    @GetMapping
    public List<Dispozitiv> getAllDispozitive() {
        return dispozitivService.getAllDispozitive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispozitiv> getDispozitivById(@PathVariable String id) {
        Optional<Dispozitiv> dispozitiv = dispozitivService.getDispozitivById(id);
        return dispozitiv.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
