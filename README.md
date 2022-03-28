# common
## 底层依赖包
- 包括SpringBoot SpringCloud Hutool JWT解析工具等
- 该依赖包只能被常规Servlet容器使用，不可作用于WebFlux等其他web容器的应用（当前架构中的Spring Cloud Gateway不可依赖此包）
