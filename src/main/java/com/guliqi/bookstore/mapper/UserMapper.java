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
            "ether_address = #{ether_address,jdbcType=VARCHAR}",
            "where user_id = #{user_id,jdbcType=VARCHAR}"
    })
    int updateById(User record);

    @Insert({
            "insert into JavaEE.User (user_id, phone, ",
            "password, gender, nickname, ",
            "IDcard, realname, ether_address)",
            "values (#{user_id,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
            "#{password,jdbcType=VARCHAR}, #{gender,jdbcType=CHAR}, #{nickname,jdbcType=VARCHAR}, ",
            "#{idcard,jdbcType=VARCHAR}, #{realname,jdbcType=VARCHAR}, #{ether_address,jdbcType=VARCHAR})"
    })
    int insert(User record);

    // 返回 phone, gender, nickname, ether_address
    @Select({"select phone, gender, nickname, ether_address from JavaEE.User where user_id = #{user_id,jdbcType=VARCHAR}"})
    User simpleSelectById(String user_id);

    // 返回 user_id, phone, gender, nickname, idcard, realname, ether_address
    @Select({"select user_id, phone, gender, nickname, idcard, realname, ether_address from JavaEE.User where user_id = #{user_id,jdbcType=VARCHAR}"})
    User detailSelectById(String user_id);

    // 返回 user_id, password
    @Select({"select user_id, password from JavaEE.User where user_id = #{user_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.VARCHAR, id=true),
    })
    User selectPwdById(String user_id);

    // 返回 user_id, phone, gender, nickname, idcard, realname
    // 懒加载 store, order, shoppinglist, address, application
    @Select({"select user_id, phone, gender, nickname, idcard, realname, ether_address from JavaEE.User where user_id = #{user_id,jdbcType=VARCHAR}"})
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