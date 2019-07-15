package ${groupId}.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The GatewayRoute Definition Object for the Gateway Server
 *
 * @Author:David.che
 * @version: ${version}
 * @Date 2019-03-01
 * @category ${groupId}:${artifactId}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRoute {
    private String routeId;
    private String uri;
    private Integer order;
    private List<GatewayPredicateDefinition> predicates;
    private List<GatewayFilterDefinition> filters;

    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String status;
}