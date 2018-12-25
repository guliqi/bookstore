package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"handler"})
@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
public class User implements Serializable {

    public interface UserDetailView{};
    public interface UserSimpleView extends UserDetailView{};

    @JsonView(UserSimpleView.class)
    private String user_id;
    @JsonView(UserSimpleView.class)
    private String phone;
    @Min(value = 6, message = "password length must not be less than 6 bits")
    private String password;
    @JsonView(UserDetailView.class)
    private String gender;
    @JsonView(UserSimpleView.class)
    private String nickname;
    @JsonView(UserDetailView.class)
    private String idcard;
    @JsonView(UserDetailView.class)
    private String realname;
    @JsonIgnore
    private Set<Store> storeSet;
    @JsonIgnore
    private Set<Order> orderSet;
    @JsonIgnore
    private Set<Shoppinglist> shoppinglistSet;
    @JsonIgnore
    private Set<Address> addressSet;
    @JsonIgnore
    private Set<Application> applicationSet;

    public User(String user_id){
        this.user_id = user_id;
    }

    public User(String phone, String password){
        this.phone = phone;
        this.password = password;
    }
}