package com.github.wololock.micronaut.products;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Singleton
final class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private static final Map<String, Supplier<Product>> products = new ConcurrentHashMap<String, Supplier<Product>>() {{
        put("PROD-001", createProduct("PROD-001", "Micronaut in Action", 29.99, 120));
        put("PROD-002", createProduct("PROD-002", "Netty in Action", 31.22, 190));
        put("PROD-003", createProduct("PROD-003", "Effective Java, 3rd edition", 31.22, 600));
        put("PROD-004", createProduct("PROD-004", "Clean Code", 31.22, 1200));
    }};

    public Maybe<Product> findProductById(final String id) {
        return Maybe.just(id).subscribeOn(Schedulers.io()).map(it -> {
            log.debug("Retrieving product with id {}", it);
            return products.getOrDefault(it, () -> null).get();
        });
    }

    private static Supplier<Product> createProduct(final String id, final String name, final Double price, final int latency) {
        return () -> {
            simulateLatency(latency);
            return new Product(id, name, BigDecimal.valueOf(price));
        };
    }

    private static void simulateLatency(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
