package com.example.demo1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
public class FooDao {
    //使用jdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addFoo(Foo foo) {
        log.info(foo.toString());
        String sql = "INSERT INTO FOO (ID, BAR) VALUES (?, ?)";
        return jdbcTemplate.update(sql, foo.getId(), foo.getBar());
    }

    public int deleteFooById(int id) {
        log.info("id: " + id);
        String sql = "DELETE FROM FOO WHERE ID = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateFoo(Foo foo) {
        log.info(foo.toString());
        String sql = "UPDATE FOO SET BAR = ? WHERE ID = ?;";
        return jdbcTemplate.update(sql, foo.getBar(), foo.getId());
    }

    public Foo selectFooById(int id) {
        log.info("id: " + id);
        String sql = "SELECT ID, BAR FROM FOO WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Foo.builder()
                        .id(rs.getInt(1))
                        .bar(rs.getString(2))
                        .build();
            }
        }, id);
    }






}
