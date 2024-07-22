package com.victor.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "actiuni")
public class Actiune {
    @Id
    private String id;
    private String idDispozitiv;
    private String mesaj;
    private Date timestamp;

    public Actiune(String id, String dispozitivID, String mesaj, Date timestamp) {
        this.id = id;
        this.idDispozitiv = dispozitivID;
        this.mesaj = mesaj;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getIdDispozitiv() {
        return idDispozitiv;
    }

    public String getMesaj() {
        return mesaj;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
