package com.victor.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "dispozitive")
public class Dispozitiv {
    @Id
    private String id;
    private String tip;
    private String status;
    private Date ultimulUpdate;
    private String idUtilizator;

    public Dispozitiv(String id, String tip, String status, Date ultimulUpdate, String idUtilizator) {
        this.id = id;
        this.tip = tip;
        this.status = status;
        this.ultimulUpdate = ultimulUpdate;
        this.idUtilizator = idUtilizator;
    }

    public String getId() {
        return id;
    }

    public String getTip() {
        return tip;
    }

    public String getStatus() {
        return status;
    }

    public Date getUltimulUpdate() {
        return ultimulUpdate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUltimulUpdate(Date ultimulUpdate) {
        this.ultimulUpdate = ultimulUpdate;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(String idUtilizator) {
        this.idUtilizator = idUtilizator;
    }
}