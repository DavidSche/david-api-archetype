package ${groupId}.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

// import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import ${groupId}.gateway.util.JsonUtils;
import ${groupId}.common.gracefullshutdown.GracefulshutdownSpringApplication;

//  TODO 自定义过滤器--增加日志和安全过滤器

// import ${groupId}.gateway.filters.PreFilter;
// import ${groupId}.gateway.filters.PostFilter;
// import ${groupId}.gateway.filters.ErrorFilter;
// import ${groupId}.gateway.filters.RouteFilter;

/**
* The starter of the Gateway Server
* 
* @Author:David.che
* 
* @version: ${version}
*
* @category ${groupId}:${artifactId}
*
*/

// @EnableZuulProxy
@Slf4j
@RestController
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "${groupId}.gateway")
public class GatewayApp {
	
	public static void main(String[] args) {
		GracefulshutdownSpringApplication.run(GatewayApp.class, args);
		log.info("init Gateway over!!!!");
	}
	
	@RequestMapping("/")
	public Mono<ResponseEntity<Map>> index() {
		return Mono.just(
		ResponseEntity.ok(JsonUtils.buildRetBody(0, "success", "This is a message from GateWay Server!")));
	}
	
	@RequestMapping("/error")
	public Mono<ResponseEntity<Map>> error() {
		return Mono.just(new ResponseEntity<>(JsonUtils.buildRetBody(1, "error", "sorry, something went wrong!"),
		HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@GetMapping("/fallback")
	public Mono<ResponseEntity<Map>> fallback() {
		return Mono.just(new ResponseEntity<>(JsonUtils.buildRetBody(1, "error", "sorry,Fallback From Gateway Server!"),
		HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@GetMapping("/gateway-health-check")
	public Mono<ResponseEntity<Map>> myCustomCheck() {
		return Mono.just(ResponseEntity.ok(JsonUtils.buildRetBody(0, "success", "Gateway healh check OK!")));
	}
	
}
