package com.guliqi.bookstore.model;

public class Views {
    public interface WithoutUserView{}

    public interface WithoutPasswordView{}

    public interface WithUserView extends WithoutUserView, WithoutPasswordView{}
}
