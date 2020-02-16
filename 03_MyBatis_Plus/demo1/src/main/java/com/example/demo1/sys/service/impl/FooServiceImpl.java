package com.example.demo1.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo1.sys.entity.Foo;
import com.example.demo1.sys.mapper.FooMapper;
import com.example.demo1.sys.service.IFooService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-02-04
 */
@Service
@Transactional
public class FooServiceImpl extends ServiceImpl<FooMapper, Foo> implements IFooService {
    @Autowired
    public FooMapper fooMapper;

    public List<Foo> selectList(QueryWrapper<Foo> queryWrapper) {
        return fooMapper.selectList(queryWrapper);
    }

    public List<Foo> selectByBar(String bar){
        return fooMapper.selectByBar(bar);
    }

    public IPage<Foo> findByPage(Page<Foo> page) {
        return fooMapper.selectPage(page,null);
    }
}
