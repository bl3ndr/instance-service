package com.kyzrlabs.bl3ndr.instanceservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


@RestController
@CrossOrigin
@RequestMapping("public/instance")
class InstanceController: HALReady<Instance> {

    @Autowired
    private lateinit var instanceService: InstanceService


    @GetMapping
    fun list(): Flux<Instance> {
        return instanceService.listAll().map(this::addHATEOAS)
    }


    @GetMapping("/{id}")
    fun get(@PathVariable(value = "id") id: String): Mono<ResponseEntity<Instance>> {
        return instanceService.getById(id)
                .map(this::addHATEOAS)
                .map { savedInstance ->
                    ResponseEntity.ok(savedInstance)
                }
                .defaultIfEmpty(ResponseEntity.notFound().build())
    }


    @PostMapping
    fun create(@RequestBody instance: Instance): Mono<Instance>{
        instance.dateCreated = Date.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant())
        return instanceService.save(instance).map(this::addHATEOAS)
    }


    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") id: String, @RequestBody instance: Instance): Mono<ResponseEntity<Instance>> {
        return instanceService.getById(id)
                .map(this::addHATEOAS)
                .flatMap {
                    instanceService.save(instance)
                }
                .map { updatedInstance -> ResponseEntity(updatedInstance, HttpStatus.OK) }
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") id: String): Mono<ResponseEntity<Void>> {

        return instanceService.getById(id)
                .flatMap { existingInstance ->
                    instanceService.delete(existingInstance)
                            .then(Mono.just(ResponseEntity<Void>(HttpStatus.OK)))
                }
                .defaultIfEmpty(ResponseEntity(HttpStatus.NOT_FOUND))
    }


    @Autowired
    private lateinit var instanceAssetService: InstanceAssetService

    @GetMapping("/{id}/asset")
    fun getAssets(@PathVariable id: String): Flux<Asset> {
        return instanceAssetService.getAssetsForInstance(id);
    }

    override fun addHATEOAS(instance: Instance): Instance {
        instance.add(linkTo(methodOn(InstanceController::class.java).delete(instance.id!!)).withSelfRel().withType("delete"))
        instance.add(linkTo(methodOn(InstanceController::class.java).get(instance.id!!)).withSelfRel())
        instance.add(linkTo(methodOn(InstanceController::class.java).getAssets(instance.id!!)).withRel("instance.asset"))
        return instance
    }
}