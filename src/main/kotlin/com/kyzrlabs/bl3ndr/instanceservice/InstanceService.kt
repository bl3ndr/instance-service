package com.kyzrlabs.bl3ndr.instanceservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration


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
        if(instance.id.equals("")) throw IllegalArgumentException("No id provided")
        var i: Mono<Instance> = instanceRepository.save(instance)

        return i;
    }

    override fun delete(instance: Instance): Mono<Void> {
        return instanceRepository.delete(instance)
    }
}