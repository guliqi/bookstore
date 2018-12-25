package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"handler", "user"})
public class Order implements Serializable {
    private String order_id;

    private User user;

    private Book book;

    private Date create_time;

    private Integer amount;

    private Address address;

    private String comments;

    private String state;

    private Store store;

    private Float payment;
}