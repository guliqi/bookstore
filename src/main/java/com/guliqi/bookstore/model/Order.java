package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
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