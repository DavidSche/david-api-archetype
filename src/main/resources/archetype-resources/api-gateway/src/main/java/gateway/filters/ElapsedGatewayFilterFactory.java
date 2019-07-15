package ${groupId}.gateway.filters;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * The ElapsedFilter for the Gateway Server
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

@Slf4j
@Component
public class ElapsedGatewayFilterFactory extends AbstractGatewayFilterFactory<ElapsedGatewayFilterFactory.Config> {

    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";
    private static final String KEY = "withParams";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public ElapsedGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                if (startTime != null) {
                    StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath()).append(": ")
                            .append(System.currentTimeMillis() - startTime).append("ms");
                    if (config.isWithParams()) {
                        log.info("ElapsedGatewayFilterFactory----true ");
                        sb.append(" params:").append(exchange.getRequest().getQueryParams());

                    }
                    log.info(sb.toString());
                    log.info("ElapsedGatewayFilterFactory----End ");

                }
            }));
        };
    }

    public static class Config {

        private boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }

    }
}