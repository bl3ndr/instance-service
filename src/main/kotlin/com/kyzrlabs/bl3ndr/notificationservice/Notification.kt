package com.kyzrlabs.bl3ndr.notificationservice

import com.kyzrlabs.bl3ndr.instanceservice.generateHash
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@TypeAlias("notification")
@Document(collection = "notification")
data class Notification(@Id val id: String, var message: String, @CreatedDate var createdDate: Date) {
    constructor(message: String): this(String.generateHash(6), message, Date.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant()))
}