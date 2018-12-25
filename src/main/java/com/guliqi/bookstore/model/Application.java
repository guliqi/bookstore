package com.guliqi.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"handler", "user"})
public class Application implements Serializable {
    private String application_id;

    private User user;

    private String storename;

    private Address address;

    private String bank_card;

    private String state;

    private String introduction;

    private String admin;

    private Application(Builder builder){
        this.application_id = builder.application_id;
        this.user = builder.user;
        this.storename = builder.storename;
        this.address = builder.address;
        this.bank_card = builder.bank_card;
        this.state = builder.state;
        this.introduction = builder.introduction;
        this.admin = builder.admin;
    }

    public static class Builder{
        private String application_id;

        private User user;

        private String storename;

        private Address address;

        private String bank_card;

        private String state;

        private String introduction;

        private String admin;

        public Builder application_id(String application_id){
            this.application_id = application_id;
            return this;
        }
        public Builder user(User user){
            this.user = user;
            return this;
        }
        public Builder storename(String storename){
            this.storename = storename;
            return this;
        }
        public Builder address(Address address){
            this.address = address;
            return this;
        }
        public Builder bank_card(String bank_card){
            this.bank_card = bank_card;
            return this;
        }
        public Builder state(String state){
            this.state = state;
            return this;
        }
        public Builder introduction(String introduction){
            this.introduction = introduction;
            return this;
        }
        public Builder admin(String admin){
            this.admin = admin;
            return this;
        }
        public Application build(){
            return new Application(this);
        }
    }
}