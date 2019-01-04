package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderMapper {
    @Delete({
            "delete from JavaEE.Order",
            "where order_id = #{order_id,jdbcType=VARCHAR}"
    })
    int deleteById(String order_id);

    @Update({
            "update JavaEE.Order",
            "set state = #{state,jdbcType=VARCHAR}",
            "where order_id = #{order_id,jdbcType=VARCHAR}"
    })
    int updateById(Order record);

    @Insert({
            "insert into JavaEE.Order (order_id, user_id, book_id, amount, address_id, comments, state, store_id, payment, tx_id)",
            "values (#{order_id,jdbcType=VARCHAR}, #{user.user_id,jdbcType=VARCHAR}, ",
            "#{book.book_id,jdbcType=VARCHAR}, ",
            "#{amount,jdbcType=INTEGER}, #{address.address_id,jdbcType=VARCHAR}, ",
            "#{comments,jdbcType=VARCHAR}, #{state,jdbcType=VARCHAR}, ",
            "#{store.store_id,jdbcType=VARCHAR}, #{payment,jdbcType=REAL}, #{tx_id,jdbcType=INTEGER})"
    })
    int insert(Order record);

    @Select({"select * from JavaEE.Order where order_id = #{order_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="order_id", property="order_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="book_id", property="book", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.BookMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="address_id", property="address", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="store_id", property="store", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.StoreMapper.selectById",
                            fetchType = FetchType.LAZY)),
    })
    Order selectById(String order_id);

    @Select({"select * from JavaEE.Order where user_id = #{user_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="order_id", property="order_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.EAGER)),
            @Result(column="book_id", property="book", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.BookMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="address_id", property="address", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="store_id", property="store", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.StoreMapper.selectById",
                            fetchType = FetchType.LAZY)),
    })
    Set<Order> selectByUserId(String user_id);
}