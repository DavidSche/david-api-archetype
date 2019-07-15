package ${groupId}.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Title:<br> The GatewayDynamicRouteService for the Gateway Server
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
public class GatewayDynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionRepository routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    public void refresh() {
        log.info("GatewayDynamicRouteService: refresh: ");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void add(RouteDefinition definition) {
        log.info("GatewayDynamicRouteService:add: " + definition.getId());
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        refresh();
    }

    public boolean delete(String id) {
        try {
            log.info("GatewayDynamicRouteService: delete: " + id);
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            refresh();
            return true;
        } catch (Exception e) {
            log.error("delete route error,id: " + id);
            log.error(e.getMessage());
            throw new NotFoundException("delete route error", e);
        }
    }

    public Flux<RouteDefinition> getRouteDefinitions(){
       return this.routeDefinitionWriter.getRouteDefinitions();
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}