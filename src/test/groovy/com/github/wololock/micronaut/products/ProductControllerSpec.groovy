package com.github.wololock.micronaut.products

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ProductControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer server = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    HttpClient http = HttpClient.create(server.URL)

    def "should return PROD-001"() {
        when:
        Product product = http.toBlocking().retrieve(HttpRequest.GET("/product/PROD-001"), Product)

        then:
        product.id == 'PROD-001'

        and:
        product.name == 'Micronaut in Action'

        and:
        product.price == 29.99
    }

    def "should support 404 response"() {
        when:
        http.toBlocking().exchange(HttpRequest.GET("/product/PROD-009"))

        then:
        def e = thrown HttpClientResponseException
        e.status == HttpStatus.NOT_FOUND
    }
}
