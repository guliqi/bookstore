package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.Address;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AddressMapper {
    @Delete({"delete from Address where address_id = #{address_id,jdbcType=VARCHAR}"})
    int deleteById(String address_id);

    @Update({
            "update Address",
            "set province = #{province,jdbcType=VARCHAR},",
            "city = #{city,jdbcType=VARCHAR},",
            "Line1 = #{line1,jdbcType=VARCHAR}",
            "where address_id = #{address_id,jdbcType=VARCHAR}"
    })
    int updateById(Address record);

    @Insert({
            "insert into Address (address_id, user_id, province, city, Line1)",
            "values (#{address_id,jdbcType=VARCHAR}, #{user.user_id,jdbcType=VARCHAR}, ",
            "#{province,jdbcType=VARCHAR}, #{city,jdbcType=VARCHAR}, ",
            "#{line1,jdbcType=VARCHAR})"
    })
    int insert(Address record);

    @Select({"select * from Address where address_id = #{address_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column = "address_id", property = "address_id", jdbcType=JdbcType.VARCHAR, id = true),
            @Result(column = "user_id", property = "user", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.LAZY))
    })
    Address selectById(String address_id);

    @Select({"select * from Address where user_id = #{user_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column = "address_id", property = "address_id", jdbcType=JdbcType.VARCHAR, id = true),
    })
    Set<Address> selectByUserId(String user_id);
}