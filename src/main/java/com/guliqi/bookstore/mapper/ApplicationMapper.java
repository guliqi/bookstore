package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.Application;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ApplicationMapper {
    @Delete({
        "delete from Application",
        "where application_id = #{application_id,jdbcType=VARCHAR}"
    })
    int deleteById(String application_id);

    @Insert({
            "insert into Application (application_id, user_id, storename, address_id, bank_card, state, introduction)",
            "values (#{application_id,jdbcType=VARCHAR}, #{user.user_id,jdbcType=VARCHAR}, ",
            "#{storename,jdbcType=VARCHAR}, #{address.address_id,jdbcType=VARCHAR}, ",
            "#{bank_card,jdbcType=VARCHAR}, #{state,jdbcType=VARCHAR}, ",
            "#{introduction,jdbcType=VARCHAR})"
    })
    int insert(Application record);

    @Update({
            "update Application",
            "set state = #{state,jdbcType=VARCHAR},",
            "admin = #{admin,jdbcType=VARCHAR}",
            "where application_id = #{application_id,jdbcType=VARCHAR}"
    })
    int updateStateById(Application record);

    @Select({"select * from Application where application_id = #{application_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="application_id", property="application_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="address_id", property="address", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY)),
    })
    Application selectById(String application_id);

    //返回 state
    @Select({"select state from Application where application_id = #{application_id,jdbcType=VARCHAR}"})
    String selectState(String application_id);

    @Select({"select * from Application where user_id = #{user_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="application_id", property="application_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="address_id", property="address", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY))
    })
    Set<Application> selectByUserId(String user_id);

    @Select({"select * from Application where state = 'CHECKPENDING'"})
    @Results({
            @Result(column="application_id", property="application_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="user_id", property="user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.detailSelectById",
                            fetchType = FetchType.LAZY)),
            @Result(column="address_id", property="address", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY))
    })
    Set<Application> selectAllCheckPending();
}