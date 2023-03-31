package org.example.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity

public class Staff {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "staff_id")
    private int staffId;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "picture", columnDefinition = "default NULL")
    private byte[] picture;

    @Column(length = 40, columnDefinition = "default NULL")
    private String email;

    @OneToOne // Ska denna rel ändras? ska en staff kunna jobba i flera butiker? Men isf jointable och så ser itne databasen ut.
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "active", nullable = false, columnDefinition = "default '1'")
    private byte active;

    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 40, columnDefinition = "default NULL")
    private String password;

    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddressId(Address address) {
        this.address = address;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Store getStore() {
        return store;
    }

    public void setStoreId(Store store) {
        this.store = store;
    }

    public byte getActive() {
        return active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
