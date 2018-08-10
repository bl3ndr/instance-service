package com.kyzrlabs.bl3nd.instanceservice

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface InstanceRepository: ReactiveCrudRepository<Instance, String> {

    fun getById(id: String): Mono<Instance>

    fun getByTitle(title: String): Flux<Instance>

}