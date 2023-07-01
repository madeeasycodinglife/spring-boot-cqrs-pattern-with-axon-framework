package com.madeeasy.queryapi.controller;

import com.madeeasy.commandapi.model.ProductRestModel;
import com.madeeasy.queryapi.queries.FindAllProductsQuery;
import com.madeeasy.queryapi.queries.FindProductByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductQueryController {

    private final QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public List<ProductRestModel> getAllProducts() {
        FindAllProductsQuery findAllProductsQuery = new FindAllProductsQuery();
        return queryGateway.query(findAllProductsQuery,
                        ResponseTypes.multipleInstancesOf(ProductRestModel.class))
                .join();
    }

    @GetMapping("/{productId}")
    public ProductRestModel getProductById(@PathVariable String productId) {
        FindProductByIdQuery query = new FindProductByIdQuery(productId);
        return queryGateway.query(query, ProductRestModel.class)
                .join();
    }
}

