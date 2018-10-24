package com.github.wololock.micronaut.products;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;

@Client("/product")
public interface ProductClient {

    @Get("/{id}")
    Maybe<Product> getProduct(final String id);
}
