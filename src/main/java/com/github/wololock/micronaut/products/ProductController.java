package com.github.wololock.micronaut.products;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Maybe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/product")
final class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Get("/{id}")
    public Maybe<Product> getProduct(String id) {
        log.debug("ProductController.getProduct({}) executed...", id);

        return productService.findProductById(id).onErrorComplete();
    }
}
