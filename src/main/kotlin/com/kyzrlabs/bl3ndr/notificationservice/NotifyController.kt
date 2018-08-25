package com.kyzrlabs.bl3ndr.notificationservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@CrossOrigin
@RequestMapping("public/notify")
class NotifyController {

    @Autowired
    private lateinit var notificationRepository: NotificationRepository


    @GetMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun stream(): Flux<Notification> {
        return notificationRepository.findWithTailableCursorBy()
    }


    @PutMapping
    fun create(@RequestBody notification: Notification): Mono<Notification> {
        return notificationRepository.save(notification)
    }

}