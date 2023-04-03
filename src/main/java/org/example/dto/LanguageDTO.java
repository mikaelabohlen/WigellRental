package org.example.dto;


import org.example.entities.Language;

import java.sql.Timestamp;

public class LanguageDTO {
    private int languageId;
    private String name;

    private Timestamp lastUpdate;

    public LanguageDTO(int languageId, String name, Timestamp lastUpdate) {
        this.languageId = languageId;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    static LanguageDTO fromEntity(Language language) {
        return new LanguageDTO(language.getLanguageId(), language.getName(), language.getLastUpdate());
    }
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
