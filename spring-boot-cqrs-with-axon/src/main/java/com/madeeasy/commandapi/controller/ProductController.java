package com.madeeasy.commandapi.controller;

import com.madeeasy.commandapi.commands.CreateProductCommand;
import com.madeeasy.commandapi.commands.DeleteProductCommand;
import com.madeeasy.commandapi.commands.PartialUpdateProductCommand;
import com.madeeasy.commandapi.commands.UpdateProductCommand;
import com.madeeasy.commandapi.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final CommandGateway commandGateway;

    public ProductController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductRestModel productRestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();
        commandGateway.sendAndWait(createProductCommand);
        return "Product Added";
    }

    @PutMapping("/update/{productId}")
    public String updateProduct(@PathVariable String productId, @RequestBody ProductRestModel productRestModel) {
        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .productId(productId)
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();
        commandGateway.sendAndWait(updateProductCommand);
        return "Product Updated";
    }

    @PatchMapping("/update/{productId}")
    public String partialUpdateProduct(
            @PathVariable String productId,
            @RequestBody Map<String, Object> updates) {
        PartialUpdateProductCommand partialUpdateProductCommand = PartialUpdateProductCommand.builder()
                .productId(productId)
                .updates(updates)
                .build();
        commandGateway.sendAndWait(partialUpdateProductCommand);
        return "Product Partially Updated";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder()
                .productId(productId)
                .build();
        commandGateway.sendAndWait(deleteProductCommand);
        return "Product Deleted";
    }
}


























