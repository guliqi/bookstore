package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
public class Shoppinglist implements Serializable {
    private String shoppingList_id;

    private User user;

    private Book book;

    private Integer amount;
}