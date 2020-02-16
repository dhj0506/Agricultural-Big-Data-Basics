# SpringBoot框架应用

基于SpringBoot，集成Druid、logback，使用JDBC实现数据表操作。

### 使用SpringBoot集成Druid、logback

* 数据库连接池Druid配置在application.properties文件中

  ```properties
  spring.datasource.url=jdbc:h2:mem:testdb
  spring.datasource.username=sa
  spring.datasource.password=
  spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
  
  #连接池的设置
  #初始化时建立物理连接的个数
  spring.datasource.druid.initial-size=5
  #最小连接池数量
  spring.datasource.druid.min-idle=5
  #最大连接池数量 maxIdle已经不再使用
  spring.datasource.druid.max-active=20
  #获取连接时最大等待时间，单位毫秒
  spring.datasource.druid.max-wait=60000
  #申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
  spring.datasource.druid.test-while-idle=true
  #既作为检测的间隔时间又作为testWhileIdel执行的依据
  spring.datasource.druid.time-between-eviction-runs-millis=60000
  #销毁线程时检测当前连接的最后活动时间和当前时间差大于该值时，关闭当前连接
  spring.datasource.druid.min-evictable-idle-time-millis=30000
  #用来检测连接是否有效的sql 必须是一个查询语句
  #mysql中为 select 'x'
  #oracle中为 select 1 from dual
  spring.datasource.druid.validation-query=select 'x'
  spring.datasource.druid.filters=stat,wall,slf4j
  spring.datasource.druid.web-stat-filter.enabled=true
  spring.datasource.druid.web-stat-filter.url-pattern=/*
  spring.datasource.druid.web-stat-filter.exclusions=*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*
  spring.datasource.druid.stat-view-servlet.enabled=true
  spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
  spring.datasource.druid.stat-view-servlet.login-username=admin
  spring.datasource.druid.stat-view-servlet.login-password=123456
  ```
  
* logback配置文件logging-spring.xml在resources文件夹下

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!--
      scan：当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。
      scanPeriod：设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒；当scan为true时，此属性生效。默认的时间间隔为1分钟。
      debug：当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。
  -->
  <configuration scan="false" scanPeriod="60 seconds" debug="false">
      <!-- 定义日志的根目录 -->
      <property name="LOG_HOME" value="logs"/>
      <!-- 定义日志文件名称 -->
      <property name="appName" value="docker"/>
  
      <!-- ch.qos.logback.core.ConsoleAppender 表示控制台输出 -->
      <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
          <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
              <level>trace</level>
          </filter>
  
          <!--
          日志输出格式：
  			%d表示日期时间，
  			%thread表示线程名，
  			%-5level：级别从左显示5个字符宽度
  			%logger{50} 表示logger名字最长50个字符，否则按照句点分割。
  			%msg：日志消息，
  			%n是换行符
          -->
          <layout class="ch.qos.logback.classic.PatternLayout">
              <!-- 当指定application.properties 里面 spring.profiles.active=dev 时，采用第一种格式
              <springProfileb 标签的包含的范围可大可小，自己确定即可否则采用第二种格式-->
              <springProfile name="dev">
                  <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ----> [%thread] ---> %-5level %logger{50} - %msg%n</pattern>
              </springProfile>
              <!--默认配置-->
              <springProfile name="!dev">
                  <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==== [%thread] ==== %-5level %logger{50} - %msg%n</pattern>
              </springProfile>
          </layout>
      </appender>
  
  
      <!-- 滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件 -->
      <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
          <!-- 指定日志文件的名称 -->
          <file>${LOG_HOME}/${appName}.log</file>
          <!--
          当发生滚动时，决定 RollingFileAppender 的行为，涉及文件移动和重命名
          TimeBasedRollingPolicy： 最常用的滚动策略，它根据时间来制定滚动策略，既负责滚动也负责出发滚动。
          -->
          <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
              <!--
              滚动时产生的文件的存放位置及文件名称 %d{yyyy-MM-dd}：按天进行日志滚动
              %i：当文件大小超过maxFileSize时，按照i进行文件滚动
              -->
              <fileNamePattern>${LOG_HOME}/${appName}-%d{yyyy-MM-dd}-%i.log</fileNamePattern>
              <!--
              可选节点，控制保留的归档文件的最大数量，超出数量就删除旧文件。假设设置每天滚动，
              且maxHistory是365，则只保存最近365天的文件，删除之前的旧文件。注意，删除旧文件是，
              那些为了归档而创建的目录也会被删除。
              -->
              <MaxHistory>365</MaxHistory>
              <!--
              当日志文件超过maxFileSize指定的大小时，根据上面提到的%i进行日志文件滚动
              注意此处配置SizeBasedTriggeringPolicy是无法实现按文件大小进行滚动的，必须配置timeBasedFileNamingAndTriggeringPolicy
              -->
              <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                  <maxFileSize>100MB</maxFileSize>
              </timeBasedFileNamingAndTriggeringPolicy>
          </rollingPolicy>
          <!-- 日志输出格式： -->
          <layout class="ch.qos.logback.classic.PatternLayout">
              <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [ %thread ] - [ %-5level ] [ %logger{50} : %line ] - %msg%n</pattern>
          </layout>
      </appender>
  
  
      <appender name="DEBUG_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
          <file>${LOG_HOME}/debug.log</file>
          <append>true</append>
          <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
              <fileNamePattern>${LOG_HOME}/debug/%d{yyyy-MM-dd}/debug.%i.log.zip</fileNamePattern>
              <maxFileSize>10MB</maxFileSize>
              <maxHistory>7</maxHistory>
              <totalSizeCap>20GB</totalSizeCap>
          </rollingPolicy>
          <encoder>
              <pattern>[lf-1][${SERVER_NAME}][%d{yyyy-MM-dd HH:mm:ss.SSS}][%-5level][%thread][%file:%line] - %msg%n
              </pattern>
          </encoder>
          <filter class="ch.qos.logback.classic.filter.LevelFilter">
              <level>DEBUG</level>
              <onMatch>ACCEPT</onMatch>
              <onMismatch>DENY</onMismatch>
          </filter>
      </appender>
  
      <appender name="WARN_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
          <file>${LOG_HOME}/warn.log</file>
          <append>true</append>
          <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
              <fileNamePattern>${LOG_HOME}/warn/%d{yyyy-MM-dd}/warn.%i.log.zip</fileNamePattern>
              <maxFileSize>10MB</maxFileSize>
              <maxHistory>7</maxHistory>
              <totalSizeCap>20GB</totalSizeCap>
          </rollingPolicy>
          <encoder>
              <pattern>[lf-1][${SERVER_NAME}][%d{yyyy-MM-dd HH:mm:ss.SSS}][%-5level][%thread][%file:%line] - %msg%n
              </pattern>
          </encoder>
          <filter class="ch.qos.logback.classic.filter.LevelFilter">
              <level>WARN</level>
              <onMatch>ACCEPT</onMatch>
              <onMismatch>DENY</onMismatch>
          </filter>
      </appender>
  
      <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
          <file>${LOG_HOME}/error.log</file>
          <append>true</append>
          <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
              <fileNamePattern>${LOG_HOME}/error/%d{yyyy-MM-dd}/error.%i.log.zip</fileNamePattern>
              <maxFileSize>10MB</maxFileSize>
              <maxHistory>7</maxHistory>
              <totalSizeCap>20GB</totalSizeCap>
          </rollingPolicy>
          <encoder>
              <pattern>[lf-1][${SERVER_NAME}][%d{yyyy-MM-dd HH:mm:ss.SSS}][%-5level][%thread][%file:%line] - %msg%n
              </pattern>
          </encoder>
          <filter class="ch.qos.logback.classic.filter.LevelFilter">
              <level>ERROR</level>
              <onMatch>ACCEPT</onMatch>
              <onMismatch>DENY</onMismatch>
          </filter>
      </appender>
  
      <appender name="TRACE_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
          <file>${LOG_HOME}/trace.log</file>
          <append>true</append>
          <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
              <fileNamePattern>${LOG_HOME}/trace/%d{yyyy-MM-dd}/trace.%i.log.zip</fileNamePattern>
              <maxFileSize>10MB</maxFileSize>
              <maxHistory>7</maxHistory>
              <totalSizeCap>20GB</totalSizeCap>
          </rollingPolicy>
          <encoder>
              <pattern>[lf-1][${SERVER_NAME}][%d{yyyy-MM-dd HH:mm:ss.SSS}][%-5level][%thread][%file:%line] - %msg%n
              </pattern>
          </encoder>
          <filter class="ch.qos.logback.classic.filter.LevelFilter">
              <level>TRACE</level>
              <onMatch>ACCEPT</onMatch>
              <onMismatch>DENY</onMismatch>
          </filter>
      </appender>
  
      <!--
  		logger主要用于存放日志对象，也可以定义日志类型、级别
  		name：表示匹配的logger类型前缀，也就是包的前半部分
  		level：要记录的日志级别，包括 TRACE < DEBUG < INFO < WARN < ERROR
  		additivity：作用在于children-logger是否使用 rootLogger配置的appender进行输出，
  		false：表示只用当前logger的appender-ref，true：
  		表示当前logger的appender-ref和rootLogger的appender-ref都有效
      -->
  
      <!-- Spring framework logger -->
      <logger name="org.springframework" level="debug" additivity="false"/>
  
      <!--
          root与logger是父子关系，没有特别定义则默认为root，任何一个类只会和一个logger对应，
          要么是定义的logger，要么是root，判断的关键在于找到这个logger，然后判断这个logger的appender和level。
      -->
      <root level="info">
          <appender-ref ref="CONSOLE"/>
          <appender-ref ref="FILE"/>
  
          <appender-ref ref="WARN_FILE"/>
          <appender-ref ref="ERROR_FILE"/>
      </root>
  
      <!--debug 日志级别-->
      <logger name="com.example" level="debug">
          <appender-ref ref="DEBUG_FILE"/>
      </logger>
  
  </configuration>
  
  ```

### 自定义数据表，使用JDBC实现数据表操作（CRUD）

- 数据表结构在schema.sql文件中定义

  ```sql
  CREATE TABLE FOO (ID INT, BAR VARCHAR(64));
  ```

- 初始化操作在data.sql文件中

  ```sql
  INSERT INTO FOO (ID, BAR) VALUES (1, 'aaa');
  INSERT INTO FOO (ID, BAR) VALUES (2, 'bbb');
  ```

- 使用JDBC实现数据表操作代码在Spirng Boot启动类中

  ```java
  @SpringBootApplication
  @Slf4j
  public class Demo1Application implements CommandLineRunner {
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
  ```

- 数据操作类FooDao

  ```java
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
  ```
  
- 实体类Foo

  ```java
  @Data
  @Builder
  public class Foo {
      private Integer id;
      private String bar;
  }
  ```
