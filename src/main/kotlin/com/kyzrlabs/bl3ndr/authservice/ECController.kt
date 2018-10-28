package com.kyzrlabs.bl3ndr.authservice

import com.kyzrlabs.bl3ndr.instanceservice.toHex
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/public/ec")
class ECController {

    @Autowired
    lateinit var ecService: ECService

    @RequestMapping("/keypair")
    fun generateKeyPair(): Mono<ResponseEntity<ECService.LightKeyPair>>{
        val keyPair = ecService.generateKeyPair()
        return Mono.just(ResponseEntity.ok().body(keyPair))
    }

    @RequestMapping("/server")
    fun getServerPubkey(): Mono<ResponseEntity<String>> {
        val ecPoint = ecService.generateNewServerPublicKey()

        return Mono.just(ResponseEntity.ok().body(ecPoint.encoded.toHex()))
    }

}