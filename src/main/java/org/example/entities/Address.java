package org.example.entities;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;
    @Column(length = 50, nullable = false)
    private String address;
    @Column(length = 50)
    private String address2;
    @Column(length = 20, nullable = false)
    private String district;
    @Column(name = "city_id", nullable = false) // men om fk är nullable överflödig?
    private int cityId;
    @Column(name = "postal_code", length = 10)
    private String postalCode;
    @Column(length = 20)
    private String phone;
    @Column(nullable = false)
    private Geometry location; // blir detta rätt? Dependency tillagt i pom-filen
    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;

    public Address() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Geometry getLocation() {
        return location;
    }

    public void setLocation(Geometry location) {
        this.location = location;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
