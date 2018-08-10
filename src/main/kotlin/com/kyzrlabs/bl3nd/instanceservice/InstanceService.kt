package com.kyzrlabs.bl3nd.instanceservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface InstanceService {

    fun getByTitle(title: String): Flux<Instance>

    fun getById(id: String): Mono<Instance>

    fun listAll(): Flux<Instance>

    fun save(instance: Instance): Mono<Instance>

    fun delete(instance: Instance): Mono<Void>
}

@Service("instanceService")
class InstanceServiceImpl : InstanceService {

    @Autowired
    lateinit var instanceRepository: InstanceRepository

    override fun getByTitle(title: String): Flux<Instance> {
        return instanceRepository.getByTitle(title)
    }

    override fun getById(id: String): Mono<Instance> {
        return instanceRepository.getById(id)
    }

    override fun listAll(): Flux<Instance> {
        return instanceRepository.findAll()
    }

    override fun save(instance: Instance): Mono<Instance> {
        return instanceRepository.save(instance)
    }

    override fun delete(instance: Instance): Mono<Void> {
        return instanceRepository.delete(instance)
    }
}