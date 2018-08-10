package com.kyzrlabs.bl3nd.instanceservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface InstanceAssetService {

    fun getAssetsForInstance(instanceId: String): Flux<Asset>

    fun getById(id: String): Mono<Asset>

    fun delete(asset: Asset): Mono<Void>

    fun save(asset: Asset): Mono<Asset>

}

@Service("instanceAssetService")
class InstanceAssetServiceImpl: InstanceAssetService {

    @Autowired
    private lateinit var instanceAssetRepository: InstanceAssetRepository

    override fun getAssetsForInstance(instanceId: String): Flux<Asset> {
        return instanceAssetRepository.getByInstanceId(instanceId)
    }

    override fun getById(id: String): Mono<Asset> {
        return  instanceAssetRepository.findById(id)
    }

    override fun delete(asset: Asset): Mono<Void> {
        return instanceAssetRepository.delete(asset)
    }

    override fun save(asset: Asset): Mono<Asset> {
        return instanceAssetRepository.save(asset)
    }

}