package com.madeeasy.queryapi.projection;

import com.madeeasy.commandapi.entity.Product;
import com.madeeasy.commandapi.model.ProductRestModel;
import com.madeeasy.commandapi.repository.ProductRepository;
import com.madeeasy.queryapi.queries.FindAllProductsQuery;
import com.madeeasy.queryapi.queries.FindProductByIdQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProjection {

    private final ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> handle(FindAllProductsQuery findAllProductsQuery) {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(product -> ProductRestModel.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build()
                )
                .toList();
    }

    @QueryHandler
    public ProductRestModel handle(FindProductByIdQuery query) {
        Product product = productRepository.findById(query.getProductId())
                .orElse(null);
        if (product != null) {
            return ProductRestModel.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .build();
        } else {
            return null;
        }
    }
}
