package com.victor.mongodb.service;

import com.victor.mongodb.model.Dispozitiv;
import com.victor.mongodb.model.Utilizator;
import com.victor.mongodb.repository.DispozitivRepository;
import com.victor.mongodb.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UtilizatorService {
    @Autowired
    private final UtilizatorRepository utilizatorRepository;
    @Autowired
    private DispozitivRepository dispozitivRepository;

    private final PasswordEncoder passwordEncoder;

    public UtilizatorService(UtilizatorRepository userRepository) {
        this.utilizatorRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Utilizator register(Utilizator utilizator) {
        if (utilizatorRepository.findByEmail(utilizator.getEmail()) != null) {
            throw new IllegalStateException("Email already in use");
        }
        utilizator.setParola(passwordEncoder.encode(utilizator.getParola()));
        utilizator = utilizatorRepository.save(utilizator);

        List<String> dispozitiveIds = getDefaultDevices(utilizator.getId());
        utilizator.setIdDispozitive(dispozitiveIds);
        utilizatorRepository.save(utilizator);

        return utilizator;
    }

    public Optional<Utilizator> getUserByToken(String token) {
        return utilizatorRepository.findByToken(token);
    }

    public Utilizator validateUser(String email, String password) {
        Utilizator utilizator = utilizatorRepository.findByEmail(email);
        if (utilizator != null && passwordEncoder.matches(password, utilizator.getParola())) {
            return utilizator;
        }
        return null;
    }

    public Optional<Utilizator> findById(String id) {
        return utilizatorRepository.findById(id);
    }

    public String generateToken(Utilizator utilizator) {
        String token = UUID.randomUUID().toString();
        utilizator.setToken(token);
        utilizator.setTokenExpiration(System.currentTimeMillis() + 3600000);
        utilizatorRepository.save(utilizator);
        return token;
    }

    public void invalidateToken(Utilizator utilizator) {
        utilizator.setToken(null);
        utilizator.setTokenExpiration(0);
        utilizatorRepository.save(utilizator);
    }

    private List<String> getDefaultDevices(String idUtilizator) {
        List<Dispozitiv> devices = new ArrayList<>();
        List<String> deviceIds = new ArrayList<>();
        Date now = new Date();
        String defaultStatus = "off";

        devices.add(new Dispozitiv(UUID.randomUUID().toString(), "Temperature and Humidity Sensor", defaultStatus, now, idUtilizator));
        devices.add(new Dispozitiv(UUID.randomUUID().toString(), "Ultrasonic Sensor", defaultStatus, now, idUtilizator));
        devices.add(new Dispozitiv(UUID.randomUUID().toString(), "DC Motor", defaultStatus, now, idUtilizator));
        devices.add(new Dispozitiv(UUID.randomUUID().toString(), "Buzzer", defaultStatus, now, idUtilizator));
        devices.add(new Dispozitiv(UUID.randomUUID().toString(), "Smart Light Bulb", defaultStatus, now, idUtilizator));

        for (Dispozitiv device : devices) {
            dispozitivRepository.save(device);
            deviceIds.add(device.getId());
        }

        return deviceIds;
    }
}
