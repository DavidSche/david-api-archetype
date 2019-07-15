package ${groupId}.restserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

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

@Configuration
@EnableMongoRepositories(basePackages = ["${groupId}.restserver.infrastructure.repository"])
@EnableMongoAuditing
open class MongoDBConfig(private val mongoFactory: MongoDbFactory,
                    private val mongoMappingContext: MongoMappingContext) {
    @Bean
    @Throws(Exception::class)
    open fun mongoConverter(): MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(mongoFactory)
        val mongoConverter = MappingMongoConverter(dbRefResolver, mongoMappingContext)
        // see: https://stackoverflow.com/questions/35598595/how-to-customize-mappingmongoconverter-setmapkeydotreplacement-in-spring-boot
        mongoConverter.setMapKeyDotReplacement("_")
        mongoConverter.afterPropertiesSet()
        return mongoConverter
    }

}