package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
public class Address implements Serializable {

    @JsonView(Views.WithoutUserView.class)
    private String address_id;

    private User user;
    @JsonView(Views.WithoutUserView.class)
    private String province;
    @JsonView(Views.WithoutUserView.class)
    private String city;
    @JsonView(Views.WithoutUserView.class)
    private String line1;

    public Address(String address_id){
        this.address_id = address_id;
    }
}