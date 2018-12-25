package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
public class Store implements Serializable {
    @JsonView(Views.WithoutUserView.class)
    private String store_id;
    @JsonView(Views.WithoutUserView.class)
    private String storename;
    @JsonView(Views.WithoutUserView.class)
    private String introduction;
    @JsonView(Views.WithoutUserView.class)
    private Address address;
    @JsonView(Views.WithUserView.class)
    private User shopKeeper;
    @JsonView(Views.WithUserView.class)
    private Set<Book> bookSet;
}