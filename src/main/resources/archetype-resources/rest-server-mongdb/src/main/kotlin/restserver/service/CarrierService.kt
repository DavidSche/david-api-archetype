package ${groupId}.restserver.service

import ${groupId}.restserver.config.CarriersConfig
import ${groupId}.restserver.domain.Carrier
import ${groupId}.restserver.infrastructure.repository.CarrierRepository
import org.springframework.stereotype.Service

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

@Service
class CarrierService(private val carriersConfig: CarriersConfig,
                     private val repository: CarrierRepository) {

    fun findByIATACode(iataCode: String): Carrier? {
        return repository.findByIataCodesIn(iataCode).firstOrNull()
    }

    fun retrieveAll(): Iterable<Carrier> {
        return repository.findAll()
    }

    fun load() {
        carriersConfig.carriers.forEach { carrier -> repository.save(carrier) }
    }

    fun deleteAll() {
        repository.deleteAll()
    }

}