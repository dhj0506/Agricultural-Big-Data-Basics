package com.example.demo1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@SpringBootApplication
@Slf4j
public class Demo1Application implements CommandLineRunner {
    // CommandLineRunner：用于在项目启动后执行一些代码，添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FooDao fooDao;

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConnection();
        showData();
        testAddFoo();
        showData();
        testDeleteFoo();
        showData();
        testUpdateFoo();
        showData();
        testSelectFooById();
    }

    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        log.info(conn.toString());
        conn.close();
    }

    private void showData() {
        jdbcTemplate.queryForList("SELECT * FROM FOO")
                .forEach(row -> log.info(row.toString()));
    }

    private void testAddFoo() {
        Foo foo = new Foo(5,"eee");
        log.info("添加：" + fooDao.addFoo(foo));
    }

    private void testDeleteFoo() {
        log.info("删除：" + fooDao.deleteFooById(5));
    }

    private void testUpdateFoo() {
        Foo foo = new Foo(2,"bbbbbb");
        log.info("修改：" + fooDao.updateFoo(foo));
    }

    private void testSelectFooById() {
        log.info("查询：" + fooDao.selectFooById(2));
    }
}
