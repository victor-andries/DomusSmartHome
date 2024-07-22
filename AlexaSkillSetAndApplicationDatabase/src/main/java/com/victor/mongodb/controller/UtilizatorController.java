package com.victor.mongodb.controller;

import com.victor.mongodb.model.Utilizator;
import com.victor.mongodb.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
public class UtilizatorController {
    @Autowired
    private UtilizatorService utilizatorService;


    @GetMapping("/user/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable String id) {
        Optional<Utilizator> utilizatorOptional = utilizatorService.findById(id);
        return utilizatorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/signup")
    public ResponseEntity<Utilizator> registerUtilizator(@RequestBody Utilizator user) {
        Utilizator savedUtilizator = utilizatorService.register(user);
        return ResponseEntity.ok(savedUtilizator);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<String> loginUtilizator(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("parola");
        Utilizator utilizator = utilizatorService.validateUser(email, password);
        if (utilizator != null) {
            String token = utilizatorService.generateToken(utilizator);
            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        } else {
            return ResponseEntity.status(401).body("{\"error\": \"Invalid credentials\"}");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        Optional<Utilizator> user = utilizatorService.getUserByToken(token);
        if (user.isPresent()) {
            utilizatorService.invalidateToken(user.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}