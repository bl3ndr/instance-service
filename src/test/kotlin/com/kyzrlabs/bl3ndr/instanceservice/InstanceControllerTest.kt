package com.kyzrlabs.bl3ndr.instanceservice

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(InstanceController::class)
internal class InstanceControllerTest {

    val API_PATH: String = "/public/instances"

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var instanceService: InstanceService

    @MockBean
    private lateinit var assetService: InstanceAssetService

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {

    }

    @Test
    fun `test list`() {
        //given
        val date: Date = Date.from(ZonedDateTime.now().toInstant())
        BDDMockito.given(this.instanceService.listAll())
                .willReturn(Flux.just(Instance("2", date, date, date, date, "Title")))

        //when
        webTestClient.get()
                .uri(API_PATH)
                .exchange()
                //then
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectStatus().isOk
                .expectBodyList(Instance::class.java)
    }

    @Test
    fun `test get`() {
        //given
        val date = Date.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant())
        val mockInstance = Instance("1", date, date, date, date, "Title")
        BDDMockito.given(instanceService.getById(anyString()))
                .willReturn(Mono.just(mockInstance))

        //when
        webTestClient.get()
                .uri("$API_PATH/{id}", "1")
                .exchange()
                //then
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.title").isEqualTo("Title")
    }

    @Test
    fun `test get failure`() {
        //given
        BDDMockito.given(instanceService.getById(anyString()))
                .willReturn(Mono.empty())

        //when
        webTestClient.get()
                .uri("$API_PATH/{id}", "1")
                .exchange()
                //then
                .expectStatus().isNotFound
    }

    @Test
    fun `test delete success`() {
        //given
        val date: Date = Date.from(ZonedDateTime.now().toInstant())
        var mockInstance = Instance("1", date, date, date, date, "Title")
        BDDMockito.given(instanceService.getById("2"))
                .willReturn(Mono.just(mockInstance))

        BDDMockito.given(instanceService.delete(mockInstance))
                .willReturn(Mono.empty())

        //when
        webTestClient.delete()
                .uri("$API_PATH/{id}", "2")
                .exchange()
                //then
                .expectStatus().isOk
    }


    @Test
    fun `test delete failed`() {

        //given
        BDDMockito.given(instanceService.getById("1"))
                .willReturn(Mono.empty())

        //when
        webTestClient.delete()
                .uri("$API_PATH/{id}", "1")
                .exchange()
                //then
                .expectStatus().isNotFound
    }


    @Test
    fun `test create instance`() {
        //given
        val mockInstance = Instance(title = "Title")
        val date: Date = Date.from(ZonedDateTime.now().toInstant())
        val savedInstance = Instance("1", date, date, date, date, "Title")

        BDDMockito.given(instanceService.save(mockInstance))
                .willReturn(Mono.just(savedInstance))

        //when
        webTestClient.post()
                .uri(API_PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(mockInstance), Instance::class.java)
                .exchange()
                //then
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.title").isEqualTo("Title")
    }

    @Test
    fun `test update instance`() {
        //given
        val date = Date.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant())

        val mockInstance = Instance("1", date, date, date, date, "Title")
        val updateInstance = Instance("1", date, date, date, date, "Title New")

        BDDMockito.given(instanceService.getById(anyString()))
                .willReturn(Mono.just(mockInstance))

        BDDMockito.given(instanceService.save(mockInstance))
                .willReturn(Mono.just(updateInstance))

        webTestClient.put()
                .uri("$API_PATH/{id}", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(updateInstance), Instance::class.java)
                .exchange()
                //then
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.title").isEqualTo("Title New")

    }
}