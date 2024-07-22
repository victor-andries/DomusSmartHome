package com.victor.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "reguliAutomatizare")
public class RegulaAutomatizare {
    @Id
    private String id;
    private String declansare;
    private List<String> idActiuni;
    private boolean esteActiv;

    public RegulaAutomatizare(String id, String declansare, List<String> actiune, boolean esteActiv) {
        this.id = id;
        this.declansare = declansare;
        this.idActiuni = actiune;
        this.esteActiv = esteActiv;
    }

    public String getId() {
        return id;
    }

    public String getDeclansare() {
        return declansare;
    }

    public List<String> getActiune() {
        return idActiuni;
    }

    public boolean isEsteActiv() {
        return esteActiv;
    }


}
