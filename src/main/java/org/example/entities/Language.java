package org.example.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity

public class Language {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "language_id")
    private int languageId;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


}
