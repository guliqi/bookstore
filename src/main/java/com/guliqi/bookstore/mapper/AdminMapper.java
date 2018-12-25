package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    @Delete({
        "delete from Admin",
        "where username = #{username,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String username);

    @Insert({
        "insert into Admin (username, password)",
        "values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR})"
    })
    int insert(Admin record);

    @Select({
        "select",
        "username, password",
        "from Admin",
        "where username = #{username,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR)
    })
    Admin selectByPrimaryKey(String username);

    @Update({
        "update Admin",
        "set password = #{password,jdbcType=VARCHAR}",
        "where username = #{username,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Admin record);


    @Select({
            "select * from Admin",
            "where username = #{username, jdbcType=VARCHAR} and password = #{password, jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR, id=true)
    })
    Admin findOne(Admin record);
}