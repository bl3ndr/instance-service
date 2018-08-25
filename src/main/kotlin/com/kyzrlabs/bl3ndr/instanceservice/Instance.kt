package com.kyzrlabs.bl3ndr.instanceservice

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.hateoas.Link
import org.springframework.hateoas.ResourceSupport
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@TypeAlias("instance")
@Document(collection = "instance")
data class Instance (@Id var id: String?,
                     var dateCreated: Date?,
                     var dateUpdated: Date?,
                     var instanceTimeFrom: Date?,
                     var instanceTimeTo: Date?,
                     var title: String ): ResourceSupport() {
    constructor(title: String) : this(String.generateHash(), null, Date.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant()), null, null, title)
    constructor() : this(String.generateHash(), null, Date.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant()), null, null, "")

    @Transient
    override fun getLinks(): MutableList<Link> {
        return super.getLinks()
    }
}