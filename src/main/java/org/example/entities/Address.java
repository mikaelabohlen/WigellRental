package org.example.entities;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.geolatte.geom.GeometryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "address")
@TypeDefs({
        @TypeDef(name = "geometry", typeClass = GeometryType.class)})
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;
    @Column(length = 50, nullable = false)
    private String address;
    @Column(length = 50, columnDefinition = "default NULL")
    private String address2;
    @Column(length = 20, nullable = false)
    private String district;
//    @OneToOne//
    @ManyToOne // Tomas hade denna istället för OneToOne
    @JoinColumn(name = "city_id", nullable = false) // men om fk är nullable överflödig?
    private City city;
    @Column(name = "postal_code", length = 10, columnDefinition = "default NULL")
    private String postalCode;
    @Column(length = 20)
    private String phone;

    @Transient
    GeometryFactory geometryFactory = new GeometryFactory();

    //Todo: Den här ger invalid stream header som fel.
//    @Column(nullable = false)
//    @Type(type = "geometry")
//    private Geometry location; // blir detta rätt? Dependency tillagt i pom-filen

    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
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

    public City city() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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


//    public Geometry getLocation() {
//        return location;
//    }
//
//    public void setLocation(Geometry location) {
//        this.location = location;
//    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
