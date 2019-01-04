package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
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

    private Integer tx_id;


    @Override
    public int hashCode() {
        return order_id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        assert obj instanceof Order;
        return ((Order) obj).order_id.equals(order_id);
    }
}