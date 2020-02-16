package com.example.demo1.sys.mapper;

import com.example.demo1.sys.entity.Foo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-02-04
 */
@Mapper
public interface FooMapper extends BaseMapper<Foo> {
    @Select("select * from FOO where BAR = #{bar}")
    List<Foo> selectByBar(@Param("bar") String bar);
}
