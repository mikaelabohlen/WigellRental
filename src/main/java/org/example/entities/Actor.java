package org.example.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", length = 45, nullable = false)
    private String firtsName;
    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;
    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;

    public Actor(){

    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirtsName() {
        return firtsName;
    }

    public void setFirtsName(String firtsName) {
        this.firtsName = firtsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
