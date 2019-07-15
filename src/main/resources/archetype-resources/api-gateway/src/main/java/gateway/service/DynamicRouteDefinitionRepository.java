package ${groupId}.gateway.service;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

import ${groupId}.gateway.util.RouteUtil;

/**
 * Title:<br> The GatewayDynamicRouteService for the Gateway Server
 * Descript:<br>
 *
 * @Author:David.che
 * @version: ${version}
 * @category ${groupId}:${artifactId}
 * Copyright: Copyright(c) , 2019<br>
 * Encoding: UNIX UTF-8
 * @see org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository
 */

@Slf4j
@Component
@ConditionalOnProperty(
        name = {"spring.cloud.gateway.datasource"},
        havingValue = "consul",
        matchIfMissing = true
)
@DependsOn("consulClient")
public class DynamicRouteDefinitionRepository implements RouteDefinitionRepository {

    private final Map<String, RouteDefinition> routes = synchronizedMap(new LinkedHashMap<String, RouteDefinition>());

//    private RouteDefinition definition = new RouteDefinition();

    @Autowired
    private ConsulClient client;

    /**
     * https://github.com/ifeng113/spring-cloud/
     * #spring:
     * #  cloud:
     * #    gateway:
     * #      routes:
     * #      - id: beego
     * #        uri: http://localhost:8080
     * #        predicates:
     * #        - Path=/beego/**
     * #        filters:
     * #        - StripPrefix=1
     * #      - id: webgo
     * #        uri: http://localhost:9090
     * #        predicates:
     * #        - Path=/webgo/**
     * #        filters:
     * #        - StripPrefix=1
     */
    public DynamicRouteDefinitionRepository() {

//        initRoute();

//        definition.setId("beego");
//        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080").build().toUri();
//        definition.setUri(uri);
//
//        PredicateDefinition predicate = new PredicateDefinition();
//        predicate.setName("Path");
//        Map<String, String> predicateParams = new HashMap<>(1);
//        predicateParams.put("", "/beego/**");
//        predicate.setArgs(predicateParams);
//
//        FilterDefinition filter = new FilterDefinition();
//        filter.setName("StripPrefix");
//        Map<String, String> filterParams = new HashMap<>(1);
//        filterParams.put("_genkey_0", "1");
//        filter.setArgs(filterParams);
//
//        definition.setPredicates(Collections.singletonList(predicate));
//        definition.setFilters(Collections.singletonList(filter));
//
//        RouteDefinition definition2 = new RouteDefinition();
//        definition2.setId("webgo");
//        URI uri2 = UriComponentsBuilder.fromHttpUrl("http://localhost:9090").build().toUri();
//        definition2.setUri(uri2);
//
//        PredicateDefinition predicate2 = new PredicateDefinition();
//        predicate2.setName("Path");
//        Map<String, String> predicateParams2 = new HashMap<>(1);
//        predicateParams2.put("", "/webgo/**");
//        predicate2.setArgs(predicateParams2);
//
//        FilterDefinition filter2 = new FilterDefinition();
//        filter2.setName("StripPrefix");
//        Map<String, String> filterParams2 = new HashMap<>(1);
//        filterParams2.put("_genkey_0", "1");
//        filter2.setArgs(filterParams2);
//
//        definition2.setPredicates(Arrays.asList(predicate2));
//        definition2.setFilters(Arrays.asList(filter2));
//
//        routes.put("beego", definition);
//        routes.put("webgo", definition2);
    }

    @PostConstruct
    public void initRoute() {
        log.info("init DynamicRouteDefinitionRepository routes begin !!!!");
        List catalogServices = new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT).getValue()
                .keySet());
//        List catalogServices2 = new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT).getValue().values());

        for (int i = 0; i < catalogServices.size(); i++) {
            String serviceName = (String) catalogServices.get(i);
            log.info("get CatalogServices Name " + serviceName);
            routes.put(serviceName, RouteUtil.newRoute(serviceName));
            log.info("add route to Gateway, Name: " + serviceName);
//
        }
        log.info("init DynamicRouteDefinitionRepository routes over !!!!");
    }


    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routes.values());
    }

    /**
     * refresh Gateway routes
     */
    public void refresh() {
        initRoute();
//        routes.remove("beego");
//
//        routes.put("beego2", definition);
    }
}
