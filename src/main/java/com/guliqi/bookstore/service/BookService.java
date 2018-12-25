package com.guliqi.bookstore.service;

import com.alibaba.fastjson.JSONObject;
import com.guliqi.bookstore.mapper.BookMapper;
import com.guliqi.bookstore.mapper.StoreMapper;
import com.guliqi.bookstore.mapper.UserMapper;
import com.guliqi.bookstore.model.Book;
import com.guliqi.bookstore.model.Store;
import com.guliqi.bookstore.model.User;
import com.guliqi.bookstore.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookService {
    private BookMapper bookMapper;
    private StoreMapper storeMapper;

    @Autowired
    public BookService(BookMapper bookMapper, StoreMapper storeMapper){
        this.bookMapper = bookMapper;
        this.storeMapper = storeMapper;
    }

    public JSONObject addBook(Book book, String user_id){
        JSONObject jsonObject = new JSONObject();
        Set<Store> stores = storeMapper.selectByUserId(user_id);
        boolean storeExists = false;
        for (Store store : stores){
            if (store.getStore_id().equals(book.getStore().getStore_id())) {
                storeExists = true;
                break;
            }
        }
        if (!storeExists)
            throw new RuntimeException("store_id错误");
        book.setBook_id(CommonUtil.UUID());
        if (bookMapper.insert(book) < 1)
            jsonObject.put("message", "insert failed");
        else
            jsonObject.put("message", "success");
        return jsonObject;
    }

    public JSONObject bookDetail(String book_id){
        JSONObject jsonObject = new JSONObject();
        Book book = bookMapper.selectById(book_id);
        if (book != null) {
            jsonObject.put("contents", book);
            jsonObject.put("message", "success");
        }
        else jsonObject.put("message", "book does not exists");
        return jsonObject;
    }
}
