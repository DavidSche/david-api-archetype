package ${groupId}.gateway.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器模型
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

@Data
public class GatewayFilterDefinition {

    //Filter Name
    private String name;
    //对应的路由规则
    private Map<String, String> args = new LinkedHashMap<>();

}
