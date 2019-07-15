package ${groupId}.restserver.api

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

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
class CarrierRouter(private val handler: CarrierHandler) {

    @Bean
    fun router() = router {
        POST("/carriers/load") {
            handler.load()
        }
        GET("/carriers") {
            handler.fetchAll()
        }
        DELETE("/carriers"){
            handler.deleteALl()
        }

        GET("/carrier/{code}") {
            handler.fetchById(it)
        }

//        PUT("/carrier/{code}") {
//            handler.updateCarrier(it)
//        }
//
//        PATCH("/carrier/{code}") {
//            handler.patchById(it)
//        }
//
//        DELETE("/carrier/{code}") {
//            handler.deleteById(it)
//        }
    }

}