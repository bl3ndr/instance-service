package com.kyzrlabs.bl3ndr.instanceservice

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface InstanceAssetRepository: ReactiveCrudRepository<Asset, String> {

    fun getByInstanceId(instanceId: String): Flux<Asset>

}
