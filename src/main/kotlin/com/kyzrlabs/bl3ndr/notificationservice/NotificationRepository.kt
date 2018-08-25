package com.kyzrlabs.bl3ndr.notificationservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NotificationRepository: ReactiveMongoRepository<String, Notification> {

    @Tailable
    fun findWithTailableCursorBy(): Flux<Notification>

    fun save(notification: Notification): Mono<Notification>

}