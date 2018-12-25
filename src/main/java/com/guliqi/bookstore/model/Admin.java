package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Admin implements Serializable {
    private String username;

    private String password;
    @JsonIgnore
    private Set<Application> applicationSet;
}