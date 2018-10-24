package com.github.wololock.micronaut.recommendation;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Observable;
import io.reactivex.Single;
import com.github.wololock.micronaut.products.Product;
import com.github.wololock.micronaut.products.ProductClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller("/recommendations")
final class RecommendationController {

    private static final Logger log = LoggerFactory.getLogger(RecommendationController.class);

    private static final List<String> ids = Arrays.asList("PROD-001", "PROD-002", "PROD-003", "PROD-004");

    private final ProductClient productClient;

    public RecommendationController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Get
    public Single<List<Product>> getRecommendations() {
        log.debug("RecommendationController.getRecommendations() called...");

        return Observable.fromIterable(ids)
                .flatMap(id -> productClient.getProduct(id).toObservable().timeout(250, TimeUnit.MILLISECONDS, Observable.empty()))
                .toList();
    }
}
