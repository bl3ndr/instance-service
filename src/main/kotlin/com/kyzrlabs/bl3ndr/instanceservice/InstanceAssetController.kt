package com.kyzrlabs.bl3ndr.instanceservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@CrossOrigin
@RequestMapping("public/instance/{id}/asset")
class InstanceAssetController : HALReady<Asset> {

    @Autowired
    private lateinit var instanceAssetService: InstanceAssetService

    @GetMapping(value = "/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun stream(@PathVariable id: String): Flux<Asset> {
        return instanceAssetService.getAssetsForInstance(id)
    }


    @GetMapping("/{id}")
    fun get(@PathVariable id: String): Mono<ResponseEntity<Asset>> {
        return instanceAssetService.getById(id)
                .map { asset ->
                    ResponseEntity.ok(asset)
                }
                .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<ResponseEntity<Void>> {
        return instanceAssetService.getById(id)
                .flatMap { asset ->
                    instanceAssetService.delete(asset)
                            .then(Mono.just(ResponseEntity<Void>(HttpStatus.OK)))
                }
                .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun create(@RequestBody asset: Asset): Mono<Asset> {
        asset.id = String.generateHash()
        return instanceAssetService.save(asset)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody asset: Asset): Mono<ResponseEntity<Asset>> {
        return instanceAssetService.getById(id)
                .flatMap {
                    instanceAssetService.save(asset)
                }
                .map { updatedInstance -> ResponseEntity.ok(updatedInstance) }
                .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    override fun addHATEOAS(asset: Asset): Asset {
        asset.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(InstanceAssetController::class.java).get(asset.id!!)).withSelfRel())
        asset.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(InstanceAssetController::class.java).delete(asset.id!!)).withSelfRel())
        return asset
    }

}
