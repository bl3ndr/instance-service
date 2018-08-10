package com.kyzrlabs.bl3nd.instanceservice

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.hateoas.ResourceSupport
import java.util.*

@TypeAlias("instance")
data class Instance (@Id var id: String?,
                     var dateCreated: Date?,
                     var dateUpdated: Date?,
                     var instanceTimeFrom: Date?,
                     var instanceTimeTo: Date?,
                     var title: String ): ResourceSupport() {
    constructor(title: String) : this("", null, null, null, null, title)
}