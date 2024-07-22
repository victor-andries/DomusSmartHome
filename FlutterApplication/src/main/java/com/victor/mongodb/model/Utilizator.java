package com.victor.mongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "utilizatori")
public class Utilizator {
    @Id
    private String id;
    private String email;
    private String nume;
    private String parola;
    private String token;
    private List<String> idDispozitive;
    private long tokenExpiration;

    public Utilizator(String id, String email, String nume, String parola) {
        this.id = id;
        this.email = email;
        this.nume = nume;
        this.parola = parola;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNume() {
        return nume;
    }

    public String getParola() {
        return parola;
    }

    public String getToken() {
        return token;
    }

    public List<String> getIdDispozitive() {
        return idDispozitive;
    }

    public long getTokenExpiration() {
        return tokenExpiration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setIdDispozitive(List<String> idDispozitive) {
        this.idDispozitive = idDispozitive;
    }

    public void setTokenExpiration(long tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }
}

