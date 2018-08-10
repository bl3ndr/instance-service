package com.kyzrlabs.bl3nd.instanceservice

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.hateoas.ResourceSupport
import java.net.URI

@TypeAlias("asset")
data class Asset(@Id var id: String?, val instanceId: String, val name: String, val uri: URI): ResourceSupport()