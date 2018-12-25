package com.guliqi.bookstore.mapper;

import com.guliqi.bookstore.model.Book;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookMapper {
    @Delete({
        "delete from Book",
        "where book_id = #{book_id,jdbcType=VARCHAR}"
    })
    int deleteById(String book_id);

    @Update({
        "update Book",
        "set store_id = #{store_id,jdbcType=VARCHAR},",
          "bookname = #{bookname,jdbcType=VARCHAR},",
          "stock = #{stock,jdbcType=INTEGER},",
          "price = #{price,jdbcType=REAL},",
          "author = #{author,jdbcType=VARCHAR},",
          "version = #{version,jdbcType=INTEGER},",
          "press = #{press,jdbcType=VARCHAR},",
          "introduction = #{introduction,jdbcType=VARCHAR}",
        "where book_id = #{book_id,jdbcType=VARCHAR}"
    })
    int updateById(Book record);

    @Insert({
            "insert into Book (book_id, store_id, bookname, stock, price, author, version, press, introduction)",
            "values (#{book_id,jdbcType=VARCHAR}, #{store.store_id,jdbcType=VARCHAR}, ",
            "#{bookname,jdbcType=VARCHAR}, #{stock,jdbcType=INTEGER}, ",
            "#{price,jdbcType=REAL}, #{author,jdbcType=VARCHAR}, #{version,jdbcType=INTEGER}, ",
            "#{press,jdbcType=VARCHAR}, #{introduction,jdbcType=VARCHAR})"
    })
    int insert(Book record);

    // store信息简单查询
    @Select({"select * from Book where book_id = #{book_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="book_id", property="book_id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="store_id", property="store", jdbcType=JdbcType.VARCHAR,
                    one = @One(select = "com.guliqi.bookstore.mapper.StoreMapper.simpleSelectById",
                            fetchType = FetchType.LAZY)),
    })
    Book selectById(String book_id);

    @Select({"select book_id, bookname, price from Book where store_id = #{store_id,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="book_id", property="book_id", jdbcType=JdbcType.VARCHAR, id=true),
    })
    Set<Book> selectByStoreId(String store_id);
}