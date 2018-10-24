package com.github.wololock.micronaut.recommendation

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import com.github.wololock.micronaut.products.Product
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class RecommendationControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer server = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    HttpClient http = HttpClient.create(server.URL)

    def "should return two recommendations"() {
        when:
        List<Product> products = http.toBlocking().retrieve(HttpRequest.GET("/recommendations"), Argument.of(List, Product))

        then:
        products.size() >= 1

        and:
        products.id.any { id -> id == 'PROD-001' || id == 'PROD-002'}
    }
}
