package ${groupId}.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;


/**
 * DeRegister the consul service of the Gateway Server
 *
 * @Author:David.che
 *
 * @version: ${version}
 *
 * @Date 2019-03-01
 *
 * @category ${groupId}:${artifactId}
 *
 * @see https://stackoverflow.com/questions/46978794/enable-cors-in-spring-5-webflux/47494858#47494858
 *
 */

@Configuration
@EnableWebFlux
public class MyCORSConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*");

        registry.addMapping("/**")
                .allowedOrigins("*") // any host or put domain(s) here
                .allowedMethods("GET, POST") // put the http verbs you want allow
                .allowedHeaders("Authorization"); // put the http headers you want allow

    }
}
