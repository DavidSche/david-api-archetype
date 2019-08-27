package ${groupId}.gateway.entity;

import com.alibaba.fastjson.JSON;
import ${groupId}.gateway.dto.GatewayFilterDefinition;
import ${groupId}.gateway.dto.GatewayPredicateDefinition;
import org.springframework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRoutes {
    private Long id;

    private String routeId;

    private String routeUri;

    private Integer routeOrder;

    private Boolean isEbl;

    private Boolean isDel;

    private Date createTime;

    private Date updateTime;

    private String predicates;

    private String filters;

    private String status;

    /**
     * 获取断言集合
     * @return
     */
    public List<GatewayPredicateDefinition> getPredicateDefinition(){
        if(!StringUtils.isEmpty(this.predicates)){
            return JSON.parseArray(this.predicates , GatewayPredicateDefinition.class);
        }
        return null;
    }

    /**
     * 获取过滤器集合
     * @return
     */
    public List<GatewayFilterDefinition> getFilterDefinition(){
        if(!StringUtils.isEmpty(this.filters)){
            return JSON.parseArray(this.filters , GatewayFilterDefinition.class);
        }
        return null;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public void setRouteUri(String routeUri) {
        this.routeUri = routeUri == null ? null : routeUri.trim();
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates == null ? null : predicates.trim();
    }

    public void setFilters(String filters) {
        this.filters = filters == null ? null : filters.trim();
    }

    @Override
    public String toString() {
        return "GatewayRoutes{" +
                "id=" + id +
                ", routeId='" + routeId + '\'' +
                ", routeUri='" + routeUri + '\'' +
                ", routeOrder=" + routeOrder +
                ", isEbl=" + isEbl +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", predicates='" + predicates + '\'' +
                ", filters='" + filters + '\'' +
                '}';
    }
}