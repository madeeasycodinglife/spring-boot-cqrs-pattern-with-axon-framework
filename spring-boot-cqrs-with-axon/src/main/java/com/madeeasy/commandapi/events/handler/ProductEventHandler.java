package com.madeeasy.commandapi.events.handler;

import com.madeeasy.commandapi.entity.Product;
import com.madeeasy.commandapi.events.ProductCreatedEvent;
import com.madeeasy.commandapi.events.ProductDeletedEvent;
import com.madeeasy.commandapi.events.ProductPartiallyUpdatedEvent;
import com.madeeasy.commandapi.events.ProductUpdatedEvent;
import com.madeeasy.commandapi.repository.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductEventHandler {

    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent, product);
        productRepository.save(product);
    }

    @EventHandler
    public void on(ProductDeletedEvent event) {
        productRepository.deleteById(event.getProductId());
    }

    @EventHandler
    public void on(ProductUpdatedEvent event) {
        Product product = productRepository.findById(event.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(event.getName());
        product.setPrice(event.getPrice());
        product.setQuantity(event.getQuantity());
        productRepository.save(product);
    }

    @EventHandler
    public void on(ProductPartiallyUpdatedEvent event) {
        Product product = productRepository.findById(event.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Map<String, Object> updates = event.getUpdates();

        if (updates.containsKey("name")) {
            product.setName((String) updates.get("name"));
        }
        if (updates.containsKey("price")) {
            product.setPrice((BigDecimal) updates.get("price"));
        }
        if (updates.containsKey("quantity")) {
            product.setQuantity((Integer) updates.get("quantity"));
        }
        productRepository.save(product);
    }
}





















