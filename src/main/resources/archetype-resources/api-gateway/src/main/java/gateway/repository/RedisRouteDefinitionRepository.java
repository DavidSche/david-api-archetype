package ${groupId}.gateway.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import ${groupId}.gateway.util.JsonUtils;

/**
 * Title:<br> The RedisRouteDefinitionRepository  for the Gateway Server
 * Descript:<br>
 *
 * @Author:David.che
 * @version: ${version}
 * @category ${groupId}:${artifactId}
 * Copyright: Copyright(c) , 2019<br>
 * Encoding: UNIX UTF-8
 */
@Slf4j
@Component
@ConditionalOnProperty(name = { "spring.cloud.gateway.datasource" }, havingValue = "redis", matchIfMissing = true)
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    public static final String GATEWAY_ROUTES = "geteway_routes";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * get routes
     * 
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream().forEach(routeDefinition -> {
            routeDefinitions.add(JsonUtils.jsonToPojo(routeDefinition.toString(), RouteDefinition.class));
        });
        return Flux.fromIterable(routeDefinitions);
    }

    /**
     * add route
     *
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {

        log.info("RedisRouteDefinitionRepository： save route:...");
        return route.flatMap(routeDefinition -> {
            redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(),
                    JsonUtils.objectToJson(routeDefinition));
            return Mono.empty();
        });
    }

    /**
     * delete route
     *
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        log.info("RedisRouteDefinitionRepository：delete route:...");
        return routeId.flatMap(id -> {
            if (redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES, id)) {
                redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }
}
