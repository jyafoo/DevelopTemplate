## Springboot后端开发模板

### 版本配置

仅支持 Springboot3.x + jdk17 以上
数据库配置最好 redis5 + mysql8 以上

目前已有依赖：
- <java.version>17</java.version>
- <spring-boot.version>3.2.5</spring-boot.version>
- <fastjson2.version>2.0.36</fastjson2.version>
- <mybatis-plus-spring-boot3-starter.version>3.5.7</mybatis-plus-spring-boot3-starter.version>
- <hutool-all.version>5.8.25</hutool-all.version>
- <easyexcel.version>3.1.3</easyexcel.version>
- <transmittable-thread-local.version>2.14.4</transmittable-thread-local.version>
- <knife4j-openapi3-jakarta-spring-boot-starter.version>4.5.0</knife4j-openapi3-jakarta-spring-boot-starter.version>
- <redisson-spring-boot-starter.version>3.27.2</redisson-spring-boot-starter.version>

### 使用说明

执行resources下的sql创建用户表，修改 application.yaml 下的 redis 和 mysql 配置，启动项目并访问 http://192.168.88.1:8081/doc.html 即可使用 knife4j 调试。

注：最终需要根据实际项目的用户表修改相应业务代码！！！

### 持续优化中....