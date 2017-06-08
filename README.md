# spring-boot-demo

* 统一异常处理
* Swagger
* Druid
* Mybatis
* Redis
* MongoDB

## 使用同一的异常处理

- 配置 `GlobalExceptionHandler.java`

  ```
    @RestControllerAdvice
    public class GlobalExceptionHandler {
        private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(value = BusinessException.class)
        public JSONObject BusinessExceptionHandler(BusinessException exception) throws IOException {
            logger.info(exception.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response_code", exception.getCode());
            jsonObject.put("message", exception.getMessage());
            return jsonObject;
        }

        @ExceptionHandler(value = Exception.class)
        public JSONObject OtherExceptionHandler(Exception e) throws IOException {
            logger.error(e.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response_code", "300005");
            jsonObject.put("message", "系统异常");
            return jsonObject;
        }
    }
  ```

## 集成swagger

- 配置 `pom.xml`

  ```
      <!--swagger-->
      <dependency>
          <groupId>io.springfox</groupId>
          <artifactId>springfox-swagger2</artifactId>
          <version>2.2.2</version>
      </dependency>
      <dependency>
          <groupId>io.springfox</groupId>
          <artifactId>springfox-swagger-ui</artifactId>
          <version>2.2.2</version>
      </dependency>

  ```

- 使用

  ```
    @ApiOperation(value = "获取信息", response = Info.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
    })
    @GetMapping(value = "/get_user")
    public Info GetUser() {
        return infoMapper.findById(1);
    }
  ```

- 访问：`http://localhost:8089/swagger-ui.html`

## 集成Druid

- 配置 `appplication.yaml`
  ```
    # mysql-druid
    spring:
        datasource:
            type: com.alibaba.druid.pool.DruidDataSource
            driver-class-name: com.mysql.jdbc.Driver
            url: jdbc:mysql://localhost:3306/xus
            username: root
            password: 4321
            # 初始化大小，最小，最大
            initialSize: 5
            minIdle: 5
            maxIdle: 100
            maxActive: 200
            # 配置获取连接等待超时的时间
            maxWait: 5000
            # 每次使用连接时进行校验，会影响系统性能。默认为false
            testOnBorrow: false
            # 验证使用的SQL语句
            validationQuery: SELECT 1
            # 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除
            testWhileIdle: true
            # 每30秒运行一次空闲连接回收器（默认-1）
            timeBetweenEvictionRunsMillis: 30000
            # 池中的连接空闲30分钟后被回收（默认30分钟）
            minEvictableIdleTimeMillis: 1800000
            # 在每次空闲连接回收器线程(如果有)运行时检查的连接数量（设置为和maxIdle一样）
            numTestsPerEvictionRun: 100
  ```

- 使用`DataSourceConfig`来替换默认配置

  因为`Druid`暂时没有专门提供给`SpringBoot`的定制版本，所以需要手动配置`Druid`的属性。

  `DataSourceConfig.java`:
  ```
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.maxActive}")
    private Integer maxActive;

    @Value("${spring.datasource.initialSize}")
    private Integer initialSize;

    @Value("${spring.datasource.minIdle}")
    private Integer minIdle;

    @Value("${spring.datasource.maxWait}")
    private Integer maxWait;

    @Bean(name = "DataSource")
    public DataSource DataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        return dataSource;
    }

  ```

- 配置状态监控的`Filter`与`Servlet`

  [Filter与Servlet配置.](https://github.com/MyHerux/spring-boot-demo/tree/master/src/main/java/com/xu/demo/web/base)

  直接访问`http://localhost:8080/druid/login.html`，输入账号密码即可进入`druid`监控。



## 集成Mybatis

- 配置`DataSourceConfig`

  使用 `setMapUnderscoreToCamelCase(true)` 来将数据库的下划线格式自动转换为驼峰格式。

  ```
    @Bean(name = "TransactionManager")
    public DataSourceTransactionManager TransactionManager() {
        return new DataSourceTransactionManager(DataSource());
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("DataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }
  ```

- 推荐使用`Mapper`注解，替代复杂的`XML`文件
  ```
  @Mapper
  public interface InfoMapper {

      @Select("select * from info where id=#{id}")
      Info findById(@Param("id") Integer id);
  }
  ```

- `Mapper`的使用
  ```
    private final InfoMapper infoMapper;

    @Autowired
    public MysqlController(InfoMapper infoMapper) {
        this.infoMapper = infoMapper;
    }

    @ApiOperation(value = "获取信息", response = Info.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
    })
    @GetMapping(value = "/get_user")
    public Info GetUser() {
        return infoMapper.findById(1);
    }
  ```
## 集成Redis

- 配置 `appplication.yaml`

  ```
    redis:
        database: 0       # Redis数据库索引（默认为0）
        host:
        port:
        password: :
        timeout:    # ms
        pool:
          max-active: 12   # 连接池最大连接数（使用负值表示没有限制）
          max-wait: -1     # 连接池最大阻塞等待时间（使用负值表示没有限制）
          max-idle: 4      # 连接池中的最大空闲连接
          min-idle: 1      # 连接池中的最小空闲连接
  ```

- 使用 `StringRedisTemplate`

  ```
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @ApiOperation(value = "测试Redis")
    @GetMapping(value = "/set")
    public void set() {
        // 保存字符串
        redisTemplate.opsForValue().set("aaa", "111");

        redisTemplate.opsForValue().get("aaa");

        redisTemplate.opsForSet().add("test_set", "1111");
        redisTemplate.opsForSet().add("test_set", "2222");
    }
    }
  ```

## 集成Mongo

- 配置 `appplication.yaml`

  ```
  # mongo
  data:
    mongodb:
      database:
      host:
      port:
      username:
      password:
  ```

- 使用 `MongoTemplate`

  ```
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @GetMapping(value = "/test")
    public void mongoT1() {
        //对象存储
        String BASE_INFO_COLLECTION = "baseInfo";
        JSONObject json = new JSONObject();
        json.put("id", "123");
        mongoTemplate.insert(json, BASE_INFO_COLLECTION);

        Query query = new Query(Criteria.where("id").is("123"));
        Long count = mongoTemplate.count(query, BASE_INFO_COLLECTION);
        System.out.println("count=" + count);
    }

    @GetMapping(value = "/test2")
    public void MongoT2() {
        Info info = new Info();
        info.setId(111);
        info.setName("dou");
        mongoTemplate.save(info);
    }
  ```