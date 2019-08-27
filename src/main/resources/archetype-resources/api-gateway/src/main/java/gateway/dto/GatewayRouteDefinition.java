package ${groupId}.gateway.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由模型
 *
 * @Author:David.che
 * @version: ${version}
 * @Date 2019-03-01
 * @category ${groupId}:${artifactId}
 */

@Data
public class GatewayRouteDefinition {

    //路由的Id
    private String id;
    private String name;
    //路由断言集合配置
    private List<GatewayPredicateDefinition> predicates = new ArrayList<>();
    //路由过滤器集合配置
    private List<GatewayFilterDefinition> filters = new ArrayList<>();
    //路由规则转发的目标uri
    private String uri;
    //路由执行的顺序
    private int order = 0;
}
