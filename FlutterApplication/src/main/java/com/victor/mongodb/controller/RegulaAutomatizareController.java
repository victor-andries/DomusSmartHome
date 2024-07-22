package com.victor.mongodb.controller;

import com.victor.mongodb.model.RegulaAutomatizare;
import com.victor.mongodb.service.RegulaAutomatizareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reguli")
public class RegulaAutomatizareController {
    private final RegulaAutomatizareService regulaAutomatizareService;

    @Autowired
    public RegulaAutomatizareController(RegulaAutomatizareService regulaAutomatizareService) {
        this.regulaAutomatizareService = regulaAutomatizareService;
    }

    @GetMapping
    public List<RegulaAutomatizare> getAllReguli() {
        return regulaAutomatizareService.getAllReguli();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegulaAutomatizare> getRegulaById(@PathVariable String id) {
        Optional<RegulaAutomatizare> regula = regulaAutomatizareService.getRegulaById(id);
        return regula.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RegulaAutomatizare createRegula(@RequestBody RegulaAutomatizare regula) {
        return regulaAutomatizareService.createRegula(regula);
    }
}
