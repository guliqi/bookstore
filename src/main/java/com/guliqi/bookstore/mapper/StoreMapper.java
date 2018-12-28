package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.Store;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StoreMapper {
    @Delete({"delete from Store where store_id = #{store_id,jdbcType=VARCHAR}"})
    int deleteById(String store_id);

    @Update({
            "update Store",
            "set storename = #{storename,jdbcType=VARCHAR},",
            "introduction = #{introduction,jdbcType=VARCHAR},",
            "address_id = #{address_id,jdbcType=VARCHAR},",
            "user_id = #{user_id,jdbcType=VARCHAR}",
            "where store_id = #{store_id,jdbcType=VARCHAR}"
    })
    int updateById(Store record);


    @Insert({
            "insert into Store (store_id, storename, introduction, address_id, user_id)",
            "values (#{store_id,jdbcType=VARCHAR}, #{storename,jdbcType=VARCHAR}, ",
            "#{introduction,jdbcType=VARCHAR}, #{address.address_id,jdbcType=VARCHAR}, ",
            "#{user.user_id,jdbcType=VARCHAR})"
    })
    int insert(Store record);

    // 返回 id, name, address
    @Select({"select store_id, address_id, storename from Store where store_id = #{store_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="store_id", property="store_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="address_id", property="address", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY)),
    })
    Store simpleSelectById(String store_id);

    @Select({"select * from Store where store_id = #{store_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="store_id", property="store_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="address_id", property="address", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column = "user_id", property = "shopKeeper", jdbcType = JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.simpleSelectById",
                            fetchType = FetchType.LAZY)),
            @Result(column = "store_id", property = "bookSet",
                    many = @Many(select = "com.guliqi.bookstore.mapper.BookMapper.selectByStoreId",
                            fetchType = FetchType.LAZY))
    })
    Store selectById(String store_id);

    @Select({"select * from Store where user_id = #{user_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column = "store_id", property = "store_id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "address_id", property = "address", jdbcType = JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.AddressMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column = "user_id", property = "shopKeeper", jdbcType = JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.UserMapper.selectById",
                            fetchType = FetchType.LAZY)),
            @Result(column = "store_id", property = "bookSet",
                    many = @Many(select = "com.guliqi.bookstore.mapper.BookMapper.selectByStoreId",
                            fetchType = FetchType.LAZY))
    })
    Set<Store> selectByUserId(String user_id);
}