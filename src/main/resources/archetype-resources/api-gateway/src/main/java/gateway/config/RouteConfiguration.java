package ${groupId}.gateway.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import ${groupId}.gateway.filters.*;

import ${groupId}.gateway.routes.AdditionalRoutes;

/**
 * The RouteConfiguration for the Gateway Server init beans
 * 
 * @Author:David.che
 * 
 * @version: ${version}
 * 
 * @Date 2019-03-01
 * 
 * @category ${groupId}:${artifactId}
 *
 */

@Slf4j
@EnableAutoConfiguration
@Import(AdditionalRoutes.class)
@Configuration
public class RouteConfiguration {

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        //@formatter:off
        return builder.routes()
                .route("httpbin", r -> r.path("/httpbin/**")
                        .filters(f ->
                                f.addResponseHeader("X-TestHeader", "foobar"))
                        .uri("http://httpbin.org:80")
                )
//                .route(r -> r.path("/image/webp")
//                        .filters(f ->
//                                f.addResponseHeader("X-AnotherHeader", "baz"))
//                        .uri("http://httpbin.org:80")
//                ).route("read_body_pred", r -> r.path("/test10/test2").and().readBody(String.class,
//                        s -> {
//                            JsonObject jsonObject = (JsonObject) new Gson().fromJson(s, JsonObject.class);
//                            jsonObject.entrySet().size();
//                            String val = jsonObject.get("key").getAsString();
//
//                            return val.equalsIgnoreCase("hello");
////                            return s.get("key").getAsString().equals("hello");
//                        }).filters(f -> f.addResponseHeader("X-TestHeader", "read_body_pred"))
//                                .uri("http://httpbin.org")
//                ).route("modify_request_body", r -> r.path("/modify/test2")
//                        .filters(f -> f.addRequestHeader("X-TestHeader", "rewrite_request_upper")
//                                .modifyRequestBody(String.class, String.class,
//                                        (exchange, s) -> Mono.just(s.toUpperCase())).modifyResponseBody(String.class, String.class,
//                                        (exchange, s) -> Mono.just("12345"))
//                        ).uri("http://localhost:8005/body")
//                )
//                .route(r -> r.order(-1)
//                        .path("/test40/**").filters(f -> f.stripPrefix(2).filter(new ThrottleGatewayFilterFactory(1, 1, 5, TimeUnit.SECONDS)))
//                        .uri("http://baidu.com")
//                        .id("ThrottleGatewayFilterFactory_test")
//                ).route(r -> r.path("/test5/**").and().header("X-Next-Url", ".+")
//                        .filters(f -> f.requestHeaderToRequestUri("X-Next-Url"))
//                        .uri("http://baidu.com"))
//                .route(r -> r.path("/test6/**").and().query("url")
//                        .filters(f -> f.changeRequestUri(e -> Optional.of(URI.create(
//                                e.getRequest().getQueryParams().getFirst("url")))))
//                        .uri("http://example.com"))
//                .route(r -> r.path("/customer/**")  //在 Route 中我们添加令牌限流过滤器，这里指定了 bucket 的容量为 10 且每一秒会补充 1 个 Token。
//                        .filters(f -> f.stripPrefix(2)
//                                .filter(new RateLimitByIpGatewayFilter(10, 1, Duration.ofSeconds(1)))
//                                .filter(rateLimitByCpuGatewayFilter))
//                        .uri("http://baidu.com")
//                        .order(0)
//                        .id("throttle_customer_service"))
                .build();
        //@formatter:on
        }

        @Bean
        public GatewayFilter loggerGatewayFilter() {
                return new LoggingGatewayFilterFactory().apply(config -> {
                });

        }

        // @Bean
        // public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory() {
        // return new ElapsedGatewayFilterFactory();
        // }

        // 根据CPU负载进行限流
        @Autowired
        private RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter;
//
//        @Bean
//        public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//                return builder.routes().route(r -> r.host("**.changeuri.org").and().header("X-Next-Url")
//                                .filters(f -> f.requestHeaderToRequestUri("X-Next-Url")).uri("http://example.com"))
//                                .route(r -> r.host("**.changeuri.org").and().query("url")
//                                                .filters(f -> f.changeRequestUri(e -> Optional.of(URI.create(
//                                                                e.getRequest().getQueryParams().getFirst("url")))))
//                                                .uri("http://example.com"))
//                                .build();
//        }

//        @Bean
//        public RouterFunction<ServerResponse> testFunRouterFunction() {
//                RouterFunction<ServerResponse> route = RouterFunctions.route(RequestPredicates.path("/testfun"),
//                                request -> ServerResponse.ok().body(BodyInserters.fromObject("hello")));
//                return route;
//        }

        @Primary
        public KeyResolver userKeyResolver() {
                return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
        }

        @Bean(name = RemoteAddrKeyResolver.BEAN_NAME)
        public RemoteAddrKeyResolver remoteAddrKeyResolver() {
                return new RemoteAddrKeyResolver();
        }

//        @Bean
//        public RouteLocator retryRouteLocator(RouteLocatorBuilder builder) {
//                return builder.routes().route("retry_java", r -> r.path("/test/**").filters(f -> f.stripPrefix(1)
//                                .retry(config -> config.setRetries(2).setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)))
//                                .uri("lb://user")).build();
//        }

        /**
         * add lb
         * 
         * @Autowired private RestTemplate restTemplate;
         * 
         *            restTemplate.getForObject("http://consul-demo-provider/getWeatherConditions",String.class);
         */
        @Bean
        @LoadBalanced
        public RestTemplate restTemplate() {
                return new RestTemplate();
        }

}
