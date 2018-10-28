package com.kyzrlabs.bl3ndr.instanceservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@ComponentScan("com.kyzrlabs.bl3ndr.authservice")
class InstanceServiceApplication

fun main(args: Array<String>) {
    runApplication<InstanceServiceApplication>(*args)
}

