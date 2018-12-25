package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
public class Book implements Serializable {
    @JsonView(Views.WithoutUserView.class)
    private String book_id;
    @JsonView(Views.WithoutUserView.class)
    private Store store;
    @JsonView(Views.WithoutUserView.class)
    private String bookname;
    @JsonView(Views.WithoutUserView.class)
    private Integer stock;
    @JsonView(Views.WithoutUserView.class)
    private Float price;
    @JsonView(Views.WithoutUserView.class)
    private String author;
    @JsonView(Views.WithoutUserView.class)
    private Integer version;
    @JsonView(Views.WithoutUserView.class)
    private String press;
    @JsonView(Views.WithoutUserView.class)
    private String introduction;
    @JsonView(Views.WithoutUserView.class)
    private Integer sales;
}