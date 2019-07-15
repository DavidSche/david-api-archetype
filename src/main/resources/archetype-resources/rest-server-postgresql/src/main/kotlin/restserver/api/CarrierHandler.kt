package ${groupId}.restserver.api

import ${groupId}.restserver.service.CarrierService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * Title:<br>
 * ${groupId}:${artifactId}: CarrierService<br>
 *
 * @Author:David.che
 * @version: ${version}
 * @Copyright: Copyright(c) , 2019<br>
 * @Encoding: UNIX UTF-8
 * 
 */

@Component
class CarrierHandler(private val service: CarrierService) {
    fun fetchAll(): Mono<ServerResponse> {
        val carriers = service.retrieveAll()
        return ServerResponse.ok().body(BodyInserters.fromObject(carriers))
                .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun fetchById(request: ServerRequest): Mono<ServerResponse> {
        val code = request.pathVariable("code").toUpperCase()
        val carrier = service.findByIATACode(code)

        return if (carrier != null) {
            ServerResponse.ok().body(BodyInserters.fromObject(carrier))
        } else {
            ServerResponse.notFound().build()
        }
    }

    fun load(): Mono<ServerResponse> {
        service.load()
        return ServerResponse.ok().build()
    }

    fun deleteALl(): Mono<ServerResponse> {
        service.deleteAll()
        return ServerResponse.noContent().build()
    }

}