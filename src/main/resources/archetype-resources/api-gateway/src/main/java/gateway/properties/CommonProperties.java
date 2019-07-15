package ${groupId}.gateway.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * The starter of the Gateway Server
 * 
 * @Author:David.che
 * 
 * @version: ${version}
 * 
 * 
 * @category ${groupId}:${artifactId}
 *
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@RefreshScope
@Configuration
@ConfigurationProperties("common")
public class CommonProperties {

    String property;

}