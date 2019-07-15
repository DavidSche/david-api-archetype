package ${groupId}.restserver;

import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 * @Author:David.che
 * version: ${version}
 * Spring Boot APP ${groupId}:${artifactId} 
 *
 */
@Slf4j
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "${groupId}")
@PropertySource(value = "classpath:/additional.yml", ignoreResourceNotFound = true)
public class RestServerApp {

    public static void main(String[] args) {
        log.info("Hello ${groupId}:${artifactId} !");
        SpringApplication.run(RestServerApp.class, args);
    }

}
