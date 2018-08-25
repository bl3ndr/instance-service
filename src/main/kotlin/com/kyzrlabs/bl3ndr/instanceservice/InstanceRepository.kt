package com.kyzrlabs.bl3ndr.instanceservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface InstanceRepository: ReactiveMongoRepository<Instance, String> {

    fun getById(id: String): Mono<Instance>

    fun getByTitle(title: String): Flux<Instance>

}