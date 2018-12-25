package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"handler", "user"})
public class Address implements Serializable {
    private String address_id;

    private User user;

    private String province;

    private String city;

    private String line1;

    public Address(String address_id){
        this.address_id = address_id;
    }
}