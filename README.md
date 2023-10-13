# ProjectPractice
## 环境
IDE：[Idea](https://www.jetbrains.com/idea/)
数据库：Mysql
## 注意
### .sql文件导入
登录MySQL，将根目录的medical_system.sql文件导入数据库，注意名称
### 后端连接数据库的配置
在src/main/resources/application.yml文件里
```
server:
  port: 8081

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/medical_system?useUnicode=true & characterEncoding=utf-8 &
      useSSL=false & serverTimezone=Asia/Shanghai
    username: root
    password: 111111

mybatis:
 mapper-locations: classpath:/mapper/*.xml
 type-aliases-package: com.example.demo.entity
```
修改url中medical_system为本地数据库名称，如果是导入的可能不用改
修改username和password为自己MySQL用户名(一般都是root)和密码
