package ${groupId}.restserver.config

import ${groupId}.restserver.domain.Carrier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

import org.springframework.stereotype.Component


/**
 * Title:<br>
 * ${groupId}:${artifactId}: CarrierService<br>
 *
 * @Author:David.che
 * @version: ${version}:
 * @Copyright: Copyright(c) , 2019<br>
 * @Encoding: UNIX UTF-8
 * 
 */
 
@Component
@ConfigurationProperties(prefix = "metadata")
// https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-kotlin.html#boot-features-kotlin-configuration-properties
open class CarriersConfig {
    lateinit var test: String
    lateinit var carriers: List<Carrier>
}