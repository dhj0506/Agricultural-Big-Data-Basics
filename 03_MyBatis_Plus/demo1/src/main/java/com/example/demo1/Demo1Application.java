package com.example.demo1;

//import com.example.demo1.entity.Foo;
//import com.example.demo1.mapper.FooMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
@MapperScan("com.example.demo1.sys.mapper")
@Slf4j
public class Demo1Application implements CommandLineRunner {
    // CommandLineRunner：用于在项目启动后执行一些代码，添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    private FooMapper fooMapper;

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConnection();
        showData();
//        testSelect();
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

//    public void testSelect() {
//        log.info("----- selectAll method test begin ------");
//        List<Foo> fooList = fooMapper.selectList(null);
//        fooList.forEach(row -> log.info(row.toString()));
//        log.info("----- selectAll method test end ------");
//    }
}
