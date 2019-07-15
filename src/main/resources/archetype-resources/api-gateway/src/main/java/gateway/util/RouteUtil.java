package ${groupId}.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

/**
 * The Gateway Utils to add route for the Gateway Server
 *
 * @Author:David.che
 * @version: ${version}
 * @category ${groupId}:${artifactId}
 */

@Slf4j
public class RouteUtil {


    public static RouteDefinition newRoute(String routeName) {

        RouteDefinition definition = new RouteDefinition();
        definition.setId(routeName);
        URI uri = UriComponentsBuilder.fromUriString("lb://" + routeName).build().toUri();
        definition.setUri(uri);

        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");
        Map<String, String> predicateParams = new LinkedHashMap<>(1);
        predicateParams.put("_genkey_0", "/" + routeName + "/**");
        predicate.setArgs(predicateParams);

//
       FilterDefinition filter = new FilterDefinition();
       filter.setName("StripPrefix");
       Map<String, String> filterParams = new LinkedHashMap<>(1);
       filterParams.put("_genkey_0", "1");
       filter.setArgs(filterParams);

        // FilterDefinition filter2 = new FilterDefinition();
        // filter2.setName("RewritePath");
        // Map<String, String> filterParams2 = new LinkedHashMap<>(1);


        // filterParams2.put("_genkey_0", "/" + routeName + "/(?<path>.*)");
        // filterParams2.put("_genkey_1", "/$\\{path}");
        // filter2.setArgs(filterParams2);

        definition.setPredicates(Collections.singletonList(predicate));
        definition.setFilters(Collections.singletonList(filter));
        return definition;
    }
}
