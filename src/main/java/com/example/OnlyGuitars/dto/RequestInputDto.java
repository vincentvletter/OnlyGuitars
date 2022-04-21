package com.example.OnlyGuitars.dto;

import javax.validation.constraints.NotBlank;

public class RequestInputDto {

    @NotBlank(message = "This field has to be filled in")
    public String brand;
    @NotBlank(message = "This field has to be filled in")
    public String model;


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
}
