package ${groupId}.gateway.controller;

import ${groupId}.gateway.dto.GatewayFilterDefinition;
import ${groupId}.gateway.entity.User;
import ${groupId}.gateway.service.GatewayDynamicRouteService;
import ${groupId}.gateway.util.JsonUtils;
import ${groupId}.gateway.util.RouteUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.model.HealthService;
import com.ecwid.consul.v1.kv.model.GetBinaryValue;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;

/**
 * The starter of the Gateway Server
 *
 * @Author:David.che
 * @version: ${version}
 * @Date 2019-03-01
 * @category ${groupId}:${artifactId}
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter(AccessLevel.PROTECTED)
@RestController
//@DependsOn("gatewayDynamicRouteService")
public class ConsulClientTest {

    @Autowired
    private ConsulClient client;

    @Autowired
    private GatewayDynamicRouteService dynamicRouteService;

    @GetMapping("/consul_1")
    public String kvOperation() {
        testKVOperation();
        return "sucess k/v";
    }

    @GetMapping("/consul_2")
    public String registerServices() {
        testRegisterServices();
        return "sucess testRegisterServices";
    }

    @GetMapping("/consul_3")
    public String registerBinServices() {
        testRegisterHttpBinService();
        return "sucess testRegisterHttpBinService";
    }


    /**
     * Rigourous Test :-)
     */

    public void testKVOperation() {
        String key = "davidche";
        String value = "hello davidche from consul!";
        client.setKVValue("david-1", value);
        client.setKVValue("david-2", JsonUtils.objectToJson(new User("davidUser")));
        client.setKVBinaryValue(key, JsonUtils.objectToJson(new User("davidUser")).getBytes());

        Map<String, String> args = new HashMap<>();
        args.put("aa", "aaaa");
        args.put("bb", "bbbbb");
        GatewayFilterDefinition filterDefinition = new GatewayFilterDefinition("myfilter", args);

        client.setKVValue("filterDefinition", JsonUtils.objectToJson(filterDefinition));
        client.setKVBinaryValue("filterDefinition_1", JsonUtils.objectToJson(filterDefinition).getBytes());

        //
        RouteDefinition definition = RouteUtil.newRoute("beego2");

        //
        client.setKVValue("routeDefinition", JsonUtils.objectToJson(definition));

        Response<GetBinaryValue> kvBinaryValue = client.getKVBinaryValue(key);
        System.out.println(new String(kvBinaryValue.getValue().getValue()));
    }


    public void testRegisterServices() {
        NewService service1 = new NewService();
        service1.setId("myapp:192.168.0.46:8081");
        service1.setName("myapp");
        service1.setAddress("192.168.0.46");
        service1.setPort(8081);
        client.agentServiceRegister(service1);
        NewService service2 = new NewService();
        service2.setId("myapp:192.168.0.46:8082");
        service2.setName("myapp");
        service2.setAddress("192.168.0.46");
        service2.setPort(8082);
        client.agentServiceRegister(service2);
    }

    public void testRegisterHttpBinService() {
        NewService service1 = new NewService();
        service1.setId("httpbin.org");
        service1.setName("httpbin.org");
        service1.setAddress("54.173.32.212");
        service1.setPort(80);
        NewService.Check check = new NewService.Check();
        check.setHttp("http://httpbin.org/status/200");
        check.setInterval("10s");
        check.setTimeout("2s");
        service1.setCheck(check);
        client.agentServiceRegister(service1);
    }

    public void testQueryServices() {
        Response<List<HealthService>> healthServices = client.getHealthServices("httpbin.org", true, null);
        for (HealthService healthService : healthServices.getValue()) {
            System.out.println(healthService.getService().getId());
        }
    }


    public void getCatalogServices() {

        List catalogServices = new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT).getValue()
                .keySet());
//        List catalogServices2 = new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT).getValue().values());

        for (int i = 0; i < catalogServices.size(); i++) {
            String serviceName = (String) catalogServices.get(i);
            log.info("get CatalogServices Name " + serviceName);

            dynamicRouteService.add(RouteUtil.newRoute(serviceName));
//
        }
//        if (StringUtils.hasText(aclToken)) {
//            return new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT, aclToken).getValue()
//                    .keySet());
//        } else {
//            return new ArrayList<>(client.getCatalogServices(QueryParams.DEFAULT).getValue()
//                    .keySet());
//        }


    }

    public void testDeregister() {
        client.agentServiceDeregister("myapp:192.168.0.46:8081");
        client.agentServiceDeregister("myapp:192.168.0.46:8082");
    }

    public void testCheck() {
        NewCheck check = new NewCheck();
        check.setId("mvnsearch");
        check.setName("mvnsearch http service");
        check.setHttp("http://www.mvnsearch.org/health.json");
        check.setInterval("10s");
        check.setTimeout("2s");
        client.agentCheckRegister(check);
    }

    public void testUnCheck() {
        client.agentCheckDeregister("mvnsearch");
    }

}
