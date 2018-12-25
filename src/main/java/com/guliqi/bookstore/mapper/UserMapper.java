package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.sql.JDBCType;

@Repository
public interface UserMapper {
    @Delete({
            "delete from JavaEE.User",
            "where user_id = #{user_id,jdbcType=VARCHAR}"
    })
    int deleteById(String user_id);

    @Update({
            "update JavaEE.User",
            "set phone = #{phone,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=VARCHAR},",
            "gender = #{gender,jdbcType=CHAR},",
            "nickname = #{nickname,jdbcType=VARCHAR},",
            "IDcard = #{idcard,jdbcType=VARCHAR},",
            "realname = #{realname,jdbcType=VARCHAR}",
            "where user_id = #{user_id,jdbcType=VARCHAR}"
    })
    int updateById(User record);

    @Insert({
            "insert into JavaEE.User (user_id, phone, ",
            "password, gender, nickname, ",
            "IDcard, realname)",
            "values (#{user_id,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
            "#{password,jdbcType=VARCHAR}, #{gender,jdbcType=CHAR}, #{nickname,jdbcType=VARCHAR}, ",
            "#{idcard,jdbcType=VARCHAR}, #{realname,jdbcType=VARCHAR})"
    })
    int insert(User record);

    @Select({"select phone, gender, nickname from JavaEE.User where user_id = #{user_id,jdbcType=VARCHAR}"})
    User simpleSelectById(String user_id);

    @Select({"select * from JavaEE.User where user_id = #{user_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="IDcard", property="idcard", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="storeSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.StoreMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="orderSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.OrderMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="shoppinglistSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.ShoppinglistMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="addressSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.AddressMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="applicationSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.ApplicationMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
    })
    User selectById(String user_id);

    @Select({
            "<script>",
            "select * from JavaEE.User",
            "where 1=1",
            "<if test=\"phone!=null\">",
            "and phone = #{phone,jdbcType=VARCHAR}</if>",
            "<if test=\"password!=null and phone!=null\">",
            "and password = #{password,jdbcType=VARCHAR}</if>",
            "</script>"
    })
    @Results({
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="IDcard", property="idcard", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="storeSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.StoreMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="orderSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.OrderMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="shoppinglistSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.ShoppinglistMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="addressSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.AddressMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),
            @Result(column="user_id", property="applicationSet",
                    many=@Many(select="com.guliqi.bookstore.mapper.ApplicationMapper.selectByUserId",
                            fetchType=FetchType.LAZY)),

    })
    User selectOne(User user);
}