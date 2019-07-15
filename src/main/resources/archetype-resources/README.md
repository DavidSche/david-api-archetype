# ${artifactId}

-----

## TODO List

- 集成redis 缓存/key/value
- 集成consul :服务注册/发现 配置中心
- 集成日志处理：--common
- 集成mongo-db kotlin
- 集成安全框架
- 集成mybatis
- 集成
- 


## Swagger UI

http://localhost:8080/swagger-ui.html

### Database migration

`./mvnw compile flyway:migrate`


<!-- @GetMapping("/my-health-check")
public ResponseEntity<String> myCustomCheck() {
    String message = "Testing my healh check function";
    return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
} -->

mvn spring-boot：run


<!-- actuator/metrics/system.cpu.usage -->

-----

### api-server 测试

``` json
  {
    "id": 6,
    "organizationId": 1,
    "departmentId": 2,
    "name": "david6",
    "age": 18,
    "position": "jinan china！66！"
  }
```

/employee/1

/department/{departmentId}
/department/2

/organization/{organizationId}
/organization/1

通过API 网关访问服务
http://192.168.5.114:9090/employee/

http://192.168.5.114:9090/employee/organization/2

http://192.168.5.114:9090/employee/department/1

直接访问服务
http://192.168.5.114:8091/department/1

-----

### **consul 相关命令**

#### **启动consul**

``` bash
./consul agent -dev -client=0.0.0.0       # -dev表示开发模式运行，另外还有-server表示服务模式运行
```

输入 <http://127.0.0.1:8500/ui/> 访问Consul，可查看到默认的ui界面：

Docker 方式启动

Run Consul inside Docker (For Development) :

    docker run -d --net=host --name=dev-consul consul:latest
Run Consul in Server Mode (For Development):

    docker run -d --name=dev-consul --net=host -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt": true}' consul agent -server -bind=<external ip> -retry-join=<root agent ip> -bootstrap-expect=<number of server agents> -ui

Run Consul in Client Mode (For Development using consult server together):

  docker run -d --name=dev-consul-client --net=host -e 'CONSUL_LOCAL_CONFIG={"leave_on_terminate": true}' consul agent -bind=<external ip> -retry-join=<root agent ip>

Run Configurations to simulate Blue/Green env for RestServiceDiscoveryApp

Just trying out Consul
If you just want to run a single instance of Consul Agent to try out its functionality:

$ docker run -p 8400:8400 -p 8500:8500 -p 8600:53/udp -h node1 progrium/consul -server -bootstrap
The Web UI can be enabled by adding the -ui-dir flag:

$ docker run -p 8400:8400 -p 8500:8500 -p 8600:53/udp -h node1 progrium/consul -server -bootstrap -ui-dir /ui
We publish 8400 (RPC), 8500 (HTTP), and 8600 (DNS) so you can try all three interfaces. We also give it a hostname of node1. Setting the container hostname is the intended way to name the Consul Agent node.



Our recommended interface is HTTP using curl:

$ curl localhost:8500/v1/catalog/nodes

##### **Consul常用命令**

Consul常用命令
命令	解释	示例
agent	运行一个consul agent	consul agent -dev
join	将agent加入到consul集群	consul join IP
members	列出consul cluster集群中的members	consul members
leave	将节点移除所在集群	consul leave

##### **Consul常用命令详解**

输入consul agent --help ，可以看到consul agent 的选项，如下：

  -advertise=addr          Sets the advertise address to use
  -advertise-wan=addr      Sets address to advertise on wan instead of advertise addr
  -atlas=org/name          Sets the Atlas infrastructure name, enables SCADA.
  -atlas-join              Enables auto-joining the Atlas cluster
  -atlas-token=token       Provides the Atlas API token
  -atlas-endpoint=1.2.3.4  The address of the endpoint for Atlas integration.
  -bootstrap               Sets server to bootstrap mode
  -bind=0.0.0.0            Sets the bind address for cluster communication
  -http-port=8500          Sets the HTTP API port to listen on
  -bootstrap-expect=0      Sets server to expect bootstrap mode.
  -client=127.0.0.1        Sets the address to bind for client access.
                           This includes RPC, DNS, HTTP and HTTPS (if configured)
  -config-file=foo         Path to a JSON file to read configuration from.
                           This can be specified multiple times.
  -config-dir=foo          Path to a directory to read configuration files
                           from. This will read every file ending in ".json"
                           as configuration in this directory in alphabetical
                           order. This can be specified multiple times.
  -data-dir=path           Path to a data directory to store agent state
  -dev                     Starts the agent in development mode.
  -recursor=1.2.3.4        Address of an upstream DNS server.
                           Can be specified multiple times.
  -dc=east-aws             Datacenter of the agent (deprecated: use 'datacenter' instead).
  -datacenter=east-aws     Datacenter of the agent.
  -encrypt=key             Provides the gossip encryption key
  -join=1.2.3.4            Address of an agent to join at start time.
                           Can be specified multiple times.
  -join-wan=1.2.3.4        Address of an agent to join -wan at start time.
                           Can be specified multiple times.
  -retry-join=1.2.3.4      Address of an agent to join at start time with
                           retries enabled. Can be specified multiple times.
  -retry-interval=30s      Time to wait between join attempts.
  -retry-max=0             Maximum number of join attempts. Defaults to 0, which
                           will retry indefinitely.
  -retry-join-wan=1.2.3.4  Address of an agent to join -wan at start time with
                           retries enabled. Can be specified multiple times.
  -retry-interval-wan=30s  Time to wait between join -wan attempts.
  -retry-max-wan=0         Maximum number of join -wan attempts. Defaults to 0, which
                           will retry indefinitely.
  -log-level=info          Log level of the agent.
  -node=hostname           Name of this node. Must be unique in the cluster
  -protocol=N              Sets the protocol version. Defaults to latest.
  -rejoin                  Ignores a previous leave and attempts to rejoin the cluster.
  -server                  Switches agent to server mode.
  -syslog                  Enables logging to syslog
  -ui                      Enables the built-in static web UI server
  -ui-dir=path             Path to directory containing the Web UI resources
  -pid-file=path           Path to file to store agent PID

##### **consul agent 命令的常用选项**

-data-dir

作用：指定agent储存状态的数据目录
这是所有agent都必须的
对于server尤其重要，因为他们必须持久化集群的状态
-config-dir

作用：指定service的配置文件和检查定义所在的位置
通常会指定为"某一个路径/consul.d"（通常情况下，.d表示一系列配置文件存放的目录）
-config-file

作用：指定一个要装载的配置文件
该选项可以配置多次，进而配置多个配置文件（后边的会合并前边的，相同的值覆盖）
-dev

作用：创建一个开发环境下的server节点
该参数配置下，不会有任何持久化操作，即不会有任何数据写入到磁盘
这种模式不能用于生产环境（因为第二条）
-bootstrap-expect

作用：该命令通知consul server我们现在准备加入的server节点个数，该参数是为了延迟日志复制的启动直到我们指定数量的server节点成功的加入后启动。
-node

作用：指定节点在集群中的名称
该名称在集群中必须是唯一的（默认采用机器的host）
推荐：直接采用机器的IP
-bind

作用：指明节点的IP地址
有时候不指定绑定IP，会报Failed to get advertise address: Multiple private IPs found. Please configure one. 的异常
-server

作用：指定节点为server
每个数据中心（DC）的server数推荐至少为1，至多为5
所有的server都采用raft一致性算法来确保事务的一致性和线性化，事务修改了集群的状态，且集群的状态保存在每一台server上保证可用性
server也是与其他DC交互的门面（gateway）
-client

作用：指定节点为client，指定客户端接口的绑定地址，包括：HTTP、DNS、RPC
默认是127.0.0.1，只允许回环接口访问
若不指定为-server，其实就是-client
-join

作用：将节点加入到集群
-datacenter

作用：指定机器加入到哪一个数据中心
尝试一下：

consul agent -dev -client 10.128.223.231
发现果然可以使用<http://10.128.223.231:8500/ui > 访问。

查看consul注册的api-service服务详情
<http://localhost:8500/v1/catalog/service/api-service>

-----

相关参考文件

[github code](https://github.com/ewolff/microservice-consul/)

```yaml
version: '3'
services:
  redis:
    image: redis:3
    container_name: "redis3"
    restart: always
    ports:
      - 6379:6379
    volumes:
      - redis-data:/data
    # networks:
    #   - "redisnet"

  consul:
    image: consul:1.2.0
    restart: always
    container_name: consul_server
    # command: agent -server -bootstrap-expect=1 -client=0.0.0.0
    command: consul agent -data-dir=/tmp/consul -dev -client=0.0.0.0
    volumes:
      - consul:/tmp/consul
    ports:
      - "8500:8500"
      - "8600:8600/udp"
    links:
     - redis

  customer:
    build: ../microservice-consul-demo/microservice-consul-demo-customer
    links:
     - consul
  catalog:
    build: ../microservice-consul-demo/microservice-consul-demo-catalog
    links:
     - consul
  order:
    build: ../microservice-consul-demo/microservice-consul-demo-order
    links:
     - consul
  # nginx:
  #   build: nginx
  #   links:
  #    - consul
  #   depends_on:
  #    - consul
  #   ports:
  #    - "8080:80"
```

[bitnami](https://bitnami.com/stacksmith)


[spring-consul-example](https://github.com/indrabasak/spring-consul-example)


docker run --name local-mongo -p 27017:27017 -v mongodata:/data/db -d mongo:latest

db tools
https://blog.csdn.net/qq_39344689/article/details/85161342

https://pan.baidu.com/s/1wVUWVCx_0e9DODGfwS4cpQ

### **portainer**

``` bash
$ curl -L https://downloads.portainer.io/portainer-agent-stack.yml -o portainer-agent-stack.yml
$ docker stack deploy --compose-file=portainer-agent-stack.yml portainer
```

```
$ docker volume create portainer_data
$ docker run -d -p 9000:9000 --name portainer --restart always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer
```