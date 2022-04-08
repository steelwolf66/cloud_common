# Cloud Common
## Cloud 底层依赖包
### 1. 包含依赖
|依赖名称|版本|备注|
|:------|:------:|:------:|
|spring-boot|2.6.2|spring-boot|
|spring-cloud|2021.0.0|spring-cloud|
|spring-cloud-alibaba|2021.1|spring-cloud-alibaba|
|spring-boot-starter-web|依赖于spring boot|
|spring-boot-starter-aop|依赖于springboot|AOP|
|spring-cloud-starter-alibaba-nacos-discovery|2021.1|nacos注册中心|
|spring-cloud-starter-alibaba-nacos-config|依赖于alibabba|nacos配置中心|
|spring-boot-autoconfigure|依赖于springboot|配置中心依赖|
|spring-cloud-starter-bootstrap|依赖于spring Cloud|配置中心依赖|
|spring-boot-starter-data-redis|依赖于spring boot|Redis|
|commons-lang3|3.12.0|工具包|
|hutool-all|5.5.8|hutool工具包|
|jwt|-|JWT解析工具|

### 2.当前架构中的Gateway项目不可引用此包，因为spring-cloud-gateway使用WebFlux作为web容器

### 3.功能介绍
- ####utils

|类名|使用说明|
|:------|:------:|
|ApplicaContextProvider|获取spring上下文；获取bean对象|
|ClassUtils|Class相关工具；反射|
|HttpUtils|Http相关工具；获取request、response|
|JsonUtils|解析JSON|
|JwtUtils|解析Jwt;从token中获取当前登录用户相关信息|
|ObjectUtils|实例对象工具类；判空、操作实例对象|
|RsaUtils|Rsa工具类；获取公钥密钥|
|StringUtils|字符串工具类|
|ThreadLocalUtils|ThreadLocal工具类（待测试）|
|UuidUtils|Uuid工具类;生成32位或40位uuid|
|WebUtils|通过token，获取用户相关信息|

- ####aspect
#####@DelFill注解
使用：作用在Mapper接口的具体方法上
eg:
    @DelFill
    int deleteByIdWithFill(User entity);
    
说明：
- config
- constants
- entity
- exception
- result
- redis