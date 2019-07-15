package ${groupId}.gateway.controller;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ${groupId}.gateway.service.GatewayDynamicRouteService;
import ${groupId}.gateway.util.RouteUtil;
import ${groupId}.gateway.util.JsonUtils;

/**
 * Title:<br>
 * The Public ApiController for the Gateway Server Descript:<br>
 *
 * @Author:David.che
 * @version: 1.0-SNAPSHOT
 * @Copyright: Copyright(c) , 2019<br>
 * @Encoding: UNIX UTF-8
 * 
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter(AccessLevel.PROTECTED)
@RestController
@RequestMapping("/api/gateway")
@DependsOn("consulClient")
public class GateApiController {

    @Autowired
    private ConsulClient client;

    @Autowired
    private GatewayDynamicRouteService dynamicRouteService;

    DiscoveryClient discoveryClient;


    //    @RequestMapping(value = "",
//            produces = {"application/json"},
//            method = RequestMethod.GET)
    @GetMapping("/sync")
    public Mono<ResponseEntity<Map>> synchRegisterServices() {
        syncCatalogServices();
        return Mono.just(ResponseEntity.ok(JsonUtils.buildRetBody(0, "success", "synch service sucess!")));
    }

    private void syncCatalogServices() {
        log.info("Sync CatalogServices...");
        List catalogServices = new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT).getValue()
                .keySet());
//        List catalogServices2 = new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT).getValue().values());

        for (int i = 0; i < catalogServices.size(); i++) {
            String serviceName = (String) catalogServices.get(i);
            log.info("get CatalogServices Name " + serviceName);
            dynamicRouteService.add(RouteUtil.newRoute(serviceName));
//
        }

    }

    @GetMapping("/services")
    public Mono<ResponseEntity<Map>> getAllServices() {
        return Mono.just(ResponseEntity.ok(JsonUtils.buildRetBody(0, "success", getDiscoveryClient().getServices())));
        //return Mono.just(buildRetBody(200,"sucess",getDiscoveryClient().getServices()));
    }

    @GetMapping("/services/{serviceName}")
    public Mono<ResponseEntity<Map>> getServiceUrl(@PathVariable final String serviceName) {
        return Mono.just(ResponseEntity.ok(JsonUtils.buildRetBody(0, "success", getDiscoveryClient().getInstances(serviceName))));

    }

    /**
     * 得到routes集合
     *
     * @return
     */
    @GetMapping("/routes")
    public Flux<RouteDefinition> listRouteDefinitions() {
        return this.dynamicRouteService.getRouteDefinitions();
    }

    /**
     * 从前台接收json route 数据
     *
     * @param definition
     * @return
     */
    @PostMapping("/routes")
    public Mono<ResponseEntity<Object>> create(@RequestBody RouteDefinition definition) {
        this.dynamicRouteService.add(definition);
        return Mono.defer(() -> Mono.just(ResponseEntity.ok(JsonUtils.buildRetBody(0, "add route success", definition))));
    }

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    @PutMapping("/routes/{id}")
    public Mono<ResponseEntity<Object>> deleteRoute(@PathVariable String id) {
        if (dynamicRouteService.delete(id)) {
            return Mono.defer(() -> Mono.just(ResponseEntity.ok(JsonUtils.buildRetBody(0, "success", id))));
        } else {
            return Mono.defer(() -> Mono.just(new ResponseEntity<>(JsonUtils.buildRetBody(1, "error", "sorry, something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR)));
        }

    }


    /**
     * 删除路由
     *
     * @param id
     * @param status 0 正常，1 删除
     * @return
     */
    @PutMapping("/routes/{id}/{status}")
    public Mono<ResponseEntity<Object>> updateStatus(@PathVariable String id, @PathVariable Integer status) {
        if (status == 1) {
            if (dynamicRouteService.delete(id)) {
                return Mono.defer(() -> Mono.just(ResponseEntity.ok(JsonUtils.buildRetBody(0, "success", id))));
            } else {
                return Mono.defer(() -> Mono.just(new ResponseEntity<>(JsonUtils.buildRetBody(1, "error", "sorry, something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR)));
            }
        }
        return Mono.defer(() -> Mono.just(new ResponseEntity<>(JsonUtils.buildRetBody(1, "error", "sorry, something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

}