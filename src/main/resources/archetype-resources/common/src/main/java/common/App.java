package ${groupId}.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Hello world Spring Boot APP!
 * 
 * @Author:David.che
 * @version: ${version}
 * 
 * ${groupId}:${artifactId}
 *
 */
@SpringBootApplication
@PropertySource(value = "classpath:/additional.yml", ignoreResourceNotFound = true)
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class, args);
    }

}
