# 微服务开发框架MyAppFabric v0.1.0 使用说明

## 构建 archetype 项目

项目采用Maven 工具构建，支持基于Spring Boot 的后台API 微服务项目开发，创建的项目支持Docker 构建和部署

变更历史：
 2019-03-01 支持Spring Boot 2.1.3 项目创建及编译
 2019-03-01 支持kotlin 语言支持
 2019-03-01 加入Dockerfile 和 jib plugin 支持项目打包构建Docker镜像
 2019-03-10 ...

TODO

- 支持Mybatis数据库操作
- 支持OAuth 2.0 安全
- 多模块项目支持
- 同时支持hibernate/jpa 和 MyBatis
- 参考 FEBS-Cloud 中的代码和结合DDD设计思想，重构代码 

  ...

### 创建新项目

``` bash
mvn archetype:generate -DarchetypeGroupId=com.david.api.starter -DarchetypeArtifactId=david-api-archetype -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.david -DartifactId=david-api-demo -DinteractiveMode=false

```

```bash

mvn archetype:generate -DarchetypeGroupId=com.asimio.api.starter -DarchetypeArtifactId=david-api-archetype -DarchetypeVersion=0-SNAPSHOT -DgroupId=com.david.api -DartifactId=david-api-demo -DinteractiveMode=false  

```

```bash
mvn archetype:generate -DarchetypeArtifactId=maven-archetype-archetype -DgroupId=groupId -DartifactId=david-api-archetype -DinteractiveMode=false

```

```bash

mvn archetype:generate -DarchetypeGroupId=com.david.api.starter -DarchetypeArtifactId=david-api-archetype -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.davidche.demo -DartifactId=davidche-api-demo -DinteractiveMode=false
```

``` bash
mvn archetype:generate -DarchetypeGroupId=com.david.api.starter \
                       -DarchetypeArtifactId=david-api-archetype \
                       -DarchetypeVersion=1.0-SNAPSHOT \
                       -DgroupId=com.davidche.restful \
                       -DartifactId=cool-sample \
                       -Dversion=1.0-SNAPSHOT \
                       -DinteractiveMode=false

mvn archetype:generate -DarchetypeGroupId=com.david.api.starter -DarchetypeArtifactId=david-api-archetype -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.davidche.restful -DartifactId=cool-sample -Dversion=1.0-SNAPSHOT -DinteractiveMode=false

```

### 构建生成的项目

``` bash
mvn clean install
```

build docker image uses jib plugin 

```bash
mvn compile jib:build

docker tag 192.168.5.101:5000/myservice:0.0.4-SNAPSHOT 124.133.33.114:3101/myservice:0.0.4-SNAPSHOT 
docker image ls
docker push 124.133.33.114:3101/myservice:0.0.4-SNAPSHOT 
```

build docker image uses dockerfile-maven plugin

[https://github.com/spotify/dockerfile-maven/releases](https://github.com/spotify/dockerfile-maven/releases)

``` bash
mvn package
mvn dockerfile:build
mvn verify
mvn dockerfile:push
mvn deploy

```

### docker build  命令

docker build  -t 127.0.0.1:5000/app:latest -t 127.0.0.1:5000/app:v2.1 -f src/main/resources/Dockerfile

docker build -f src/main/resources/Dockerfile -t 127.0.0.1:5000/app:latest -t 127.0.0.1:5000/app:v2.1 .


2、在yaml里，用#做注释

3、在yaml里，用on、1、true来表示true，off、0、false来表示false



使用yaml的注意事项

1、在yaml里面，结构通过缩进来表示，yaml不支持制表符tab缩进，请使用空格缩进；
2、如果参数是以空格开始或结束的字符串，应使用单引号把他包进来。如果一个字符串参数包含特殊字符，也要用单引号包起来。下面是示例：

如果要保存类似    http://www.bai'u.com这样的数据时，下面这种写法是错误的：

website:{  baidu:     http://www.bai'u.com }#写法错误，因为没有用单引号括起来；

website:{  baidu: '    http://www.bai''u.com'}#写法正确，如果字符串中本身包含单引号，则需要用‘’进行转义；如果字符串开头或结尾包含空格，则需要用单引号将整个字符串包裹

在书写键值对时，如果键名或键值包含非英文字母和数字，应该用引号括起来，例如： '标题': '这是我的第一本杂志' 

3、每个冒号后面一定要有一个空格（以冒号结尾不需要空格，表示文件路径的模版可以不需要空格），这里指的是键值对，例如：

mykey: my_value

4、 想要表示列表项，使用一个短横杠加一个空格。多个项使用同样的缩进级别作为同一个列表的一部分

my_dictionary:
  - list_value_one
  - list_value_two
  - list_value_three
  - 
5、yaml中，空值可以用null或~表示
--------------------- 
 
原文：https://blog.csdn.net/zhengxiangwen/article/details/70042514 
 
