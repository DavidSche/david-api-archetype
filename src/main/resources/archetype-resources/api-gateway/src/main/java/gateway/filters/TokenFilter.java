package ${groupId}.gateway.filters;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

/**
 * The TokenFilter for the Gateway Server  全局过滤器的测试和使用
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
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    // http://localhost:10000/customer/hello/windmt?token=1000

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (token == null || token.isEmpty()) {
            log.info("----UNAUTHORIZED---");
            // exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);

    }

    @Override

    public int getOrder() {

        return -100;

    }

}