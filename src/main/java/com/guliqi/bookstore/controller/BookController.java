package com.guliqi.bookstore.controller;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.interceptor.UserLoginToken;
import com.guliqi.bookstore.model.Book;
import com.guliqi.bookstore.service.BookService;
import com.guliqi.bookstore.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/request/book")
public class BookController {
    private BookService bookService;
    private TokenService tokenService;

    @Autowired
    public BookController(BookService bookService, TokenService tokenService){
        this.bookService = bookService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "测试添加图书")
    @PostMapping(value = "")
    @UserLoginToken
    public JSONObject addBook(@RequestHeader String token, @RequestBody Book book){
        String user_id = tokenService.getIdOrName(token);
        return bookService.addBook(book, user_id);
    }

    @ApiOperation(value = "测试查看书籍信息")
    @GetMapping(value = "")
    public JSONObject bookDetail(@RequestParam String book_id){
        return bookService.bookDetail(book_id);
    }
}
