package com.example.OnlyGuitars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Timestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "guitars")
public class Guitar {

    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private String model;
    @JsonIgnore
    @Lob
    private byte[] image;
    @Timestamp
    private LocalDateTime timeStamp = LocalDateTime.now();

    @OneToMany(mappedBy = "guitar")
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "guitars")
    @JsonIgnore
    private List<Profile> profiles = new ArrayList<>();


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public List<Profile> getProfiles() {
        return profiles;
    }
}
