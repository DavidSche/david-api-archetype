package ${groupId}.restserver.infrastructure.repository

import ${groupId}.restserver.domain.Carrier
import org.springframework.data.repository.CrudRepository

/**
 * Title:<br>
 * ${groupId}:${artifactId}: CarrierService<br>
 *
 * @Author:David.che
 * @version: 1.0-SNAPSHOT
 * @Copyright: Copyright(c) , 2019<br>
 * @Encoding: UNIX UTF-8
 * 
 */
 
interface CarrierRepository : CrudRepository<Carrier, String> {
    fun findByIataCodesIn(iataCode: String): Iterable<Carrier>
}
