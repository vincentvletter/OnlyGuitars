package com.example.OnlyGuitars.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

public class GuitarInputDto {

    public Long id;
    @NotBlank(message = "brand can't be empty")
    public String brand;
    @NotBlank(message = "model can't be empty")
    public String model;
    @Lob
    public byte[] image;

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
}
