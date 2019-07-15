package ${groupId}.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The starter of the Gateway Server
 *
 * @Author:David.che
 * @version: ${version}
 * @Date 2019-03-01
 * @category ${groupId}:${artifactId}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayPredicateDefinition {
    private String name;
    private Map<String, String> args = new LinkedHashMap<>();
}
