# spring-boot-demo

集成Druid，Mybatis，Redis，Mongo。

- 集成Druid

  1.配置
  ```
  # Druid
  spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
  spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  spring.datasource.url=jdbc:mysql://localhost:3306/xus
  spring.datasource.username=root
  spring.datasource.password=4321
  # 下面为连接池的补充设置，应用到上面所有数据源中
  # 初始化大小，最小，最大
  spring.datasource.initialSize=5
  spring.datasource.minIdle=5
  spring.datasource.maxActive=20
  # 配置获取连接等待超时的时间
  spring.datasource.maxWait=60000
  # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
  spring.datasource.timeBetweenEvictionRunsMillis=60000
  # 配置一个连接在池中最小生存的时间，单位是毫秒
  spring.datasource.minEvictableIdleTimeMillis=300000
  spring.datasource.validationQuery=SELECT 1 FROM DUAL
  spring.datasource.testWhileIdle=true
  spring.datasource.testOnBorrow=false
  spring.datasource.testOnReturn=false
  # 打开PSCache，并且指定每个连接上PSCache的大小
  spring.datasource.poolPreparedStatements=true
  spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
  # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
  spring.datasource.filters=stat,wall,log4j
  # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
  spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
  # 合并多个DruidDataSource的监控数据
  #spring.datasource.useGlobalDataSourceStat=true
  ```
  2.状态监控的filter与servlet
  [filter与servlet配置](https://github.com/MyHerux/spring-boot-demo/tree/master/src/main/java/com/xu/demo/web/base)
  3.访问
  直接访问http://localhost:8080/druid/login.html，输入账号密码即可进入druid监控。
  
- 集成Mybatis
- 集成Redis

  1.配置
  ```
  # REDIS (RedisProperties)
  spring.redis.database=0
  spring.redis.host=
  spring.redis.password=
  spring.redis.port=
  spring.redis.pool.max-idle=100
  spring.redis.pool.min-idle=1
  spring.redis.pool.max-active=1000
  spring.redis.pool.max-wait=-1
  spring.redis.timeout=60
  ```
  
  2.使用
  ```
   @Autowired
   private StringRedisTemplate stringRedisTemplate;
   
   @Resource(name = "stringRedisTemplate")
   ValueOperations<String, String> valOpsStr;
   
   @RequestMapping(value = "/t1", method = RequestMethod.GET)
   public String redisT1() {
        valOpsStr.set("1234", "321");
        return valOpsStr.get("1234");
    }
  ```
  
- 集成Mongo

  1.配置
  ```
  # Mongo
  spring.data.mongodb.database=
  spring.data.mongodb.host=
  spring.data.mongodb.port=
  spring.data.mongodb.username=
  spring.data.mongodb.password=
  spring.data.mongodb.repositories.enabled=true
  ```
  2.使用
  ```
  @Autowired
  private MongoTemplate mongoTemplate;

  @RequestMapping(value = "/t2", method = RequestMethod.GET)
  public void mongoT1() {
       String BASE_INFO_COLLECTION = "baseInfo";
       JSONObject json = new JSONObject();
       json.put("id", "123");
       mongoTemplate.insert(json, BASE_INFO_COLLECTION);
   }

   @RequestMapping(value = "/t3", method = RequestMethod.GET)
   public long MongoT2() {
       String BASE_INFO_COLLECTION = "baseInfo";
       Query query = new Query(Criteria.where("id").is("123"));
       return mongoTemplate.count(query, BASE_INFO_COLLECTION);
   }
  ```