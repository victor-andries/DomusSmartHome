package com.victor.mongodb.controller;

import com.victor.mongodb.model.Actiune;
import com.victor.mongodb.service.ActiuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actiuni")
public class ActiuneController {
    private final ActiuneService actiuneService;

    @Autowired
    public ActiuneController(ActiuneService actiuneService) {
        this.actiuneService = actiuneService;
    }

    @GetMapping
    public List<Actiune> getAllActiuni() {
        return actiuneService.getAllActiuni();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actiune> getActiuneById(@PathVariable String id) {
        Optional<Actiune> actiune = actiuneService.getActiuneById(id);
        return actiune.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Actiune createActiune(@RequestBody Actiune actiune) {
        return actiuneService.createActiune(actiune);
    }
}
