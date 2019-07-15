package ${groupId}.gateway.filters;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern.PathMatchInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import static org.springframework.cloud.gateway.handler.predicate.CloudFoundryRouteServiceRoutePredicateFactory.X_CF_FORWARDED_URL;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

@Slf4j
@Component
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.LogConfig> {

	private static final String KEY = "withLogs";

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList(KEY);
	}

	public LoggingGatewayFilterFactory() {
		super(LogConfig.class);
	}

	@Override
	public GatewayFilter apply(LogConfig config) {
		return (exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();

			// String serviceInstanceId = getServiceInstanceId(exchange);

			URI forwardedUrl = getForwardedUrl(request);
			// 测试一下使用
			log.info(" ----- testlog filter -----");
			log.info(String.valueOf(config.isWithLogs()));
			log.info(" ----------");

			log.info("Forwarding request:  method={}, headers={}, url={}", request.getMethod(), request.getHeaders(),
					forwardedUrl);

			return chain.filter(exchange)
					.doOnSuccess(x -> log.info("Response: method={}, headers={}, url={}", request.getMethod(),
							request.getHeaders(), forwardedUrl))
					.doOnError(e -> log.error("Error: exception={},  method={}, headers={}, url={}", e,
							request.getMethod(), request.getHeaders(), forwardedUrl));
		};
	}

	private String getServiceInstanceId(ServerWebExchange exchange) {
		PathMatchInfo uriVariablesAttr = exchange.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Map<String, String> uriVariables = uriVariablesAttr.getUriVariables();
		return uriVariables.get("instanceId");
	}

	private URI getForwardedUrl(ServerHttpRequest request) {
		List<String> headers = request.getHeaders().get(X_CF_FORWARDED_URL);
		if (headers == null || headers.isEmpty()) {
			log.warn("No " + X_CF_FORWARDED_URL + " header in request");
			return null;
		}

		String forwardedUrl = headers.get(0);
		try {
			return new URI(forwardedUrl);
		} catch (URISyntaxException e) {
			log.warn("Invalid value for " + X_CF_FORWARDED_URL + " header: " + forwardedUrl);
			return null;
		}
	}

	public static class LogConfig {

		private boolean withLogs;

		public boolean isWithLogs() {
			return withLogs;
		}

		public void setWithLogs(boolean withLogs) {
			this.withLogs = withLogs;
		}

	}

}