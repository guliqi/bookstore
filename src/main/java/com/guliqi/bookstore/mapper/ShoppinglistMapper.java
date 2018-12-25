package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.Shoppinglist;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShoppinglistMapper {
    @Delete({
        "delete from ShoppingList",
        "where shoppingList_id = #{shoppingList_id,jdbcType=VARCHAR}"
    })
    int deleteById(String shoppingList_id);

    @Update({
        "update ShoppingList",
        "set user_id = #{user_id,jdbcType=VARCHAR},",
          "book_id = #{book_id,jdbcType=VARCHAR},",
          "amount = #{amount,jdbcType=INTEGER}",
        "where shoppingList_id = #{shoppingList_id,jdbcType=VARCHAR}"
    })
    int updateById(Shoppinglist record);

    @Insert({
            "insert into ShoppingList (shoppingList_id, user_id, book_id, amount)",
            "values (#{shoppingList_id,jdbcType=VARCHAR}, #{user.user_id,jdbcType=VARCHAR}, ",
            "#{book.book_id,jdbcType=VARCHAR}, #{amount,jdbcType=INTEGER})"
    })
    int insert(Shoppinglist record);

    @Select({"select * from ShoppingList where shoppingList_id = #{shoppingList_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="shoppingList_id", property="shoppingList_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="book_id", property="book", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.BookMapper.selectById",
                            fetchType = FetchType.LAZY))
    })
    Shoppinglist selectById(String shoppingList_id);

    @Select({"select * from ShoppingList where user_id = #{user_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="shoppingList_id", property="shoppingList_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.EAGER)),
            @Result(column="book_id", property="book", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.BookMapper.selectById",
                            fetchType = FetchType.LAZY))
    })
    Set<Shoppinglist> selectByUserId(String user_id);
}