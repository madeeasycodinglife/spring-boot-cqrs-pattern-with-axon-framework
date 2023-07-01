package com.madeeasy.commandapi.aggregate;

import com.madeeasy.commandapi.commands.CreateProductCommand;
import com.madeeasy.commandapi.commands.DeleteProductCommand;
import com.madeeasy.commandapi.commands.PartialUpdateProductCommand;
import com.madeeasy.commandapi.commands.UpdateProductCommand;
import com.madeeasy.commandapi.events.ProductCreatedEvent;
import com.madeeasy.commandapi.events.ProductDeletedEvent;
import com.madeeasy.commandapi.events.ProductPartiallyUpdatedEvent;
import com.madeeasy.commandapi.events.ProductUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@NoArgsConstructor
@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        // you can perform validation here
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.name = productCreatedEvent.getName();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(DeleteProductCommand command) {
        AggregateLifecycle.apply(new ProductDeletedEvent(command.getProductId()));
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent productDeletedEvent) {
        AggregateLifecycle.markDeleted();
    }

    @CommandHandler
    public void on(UpdateProductCommand updateProductCommand) {
        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent();
        BeanUtils.copyProperties(updateProductCommand, productUpdatedEvent);
        AggregateLifecycle.apply(productUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.name = event.getName();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
    }

    @CommandHandler
    public void handle(PartialUpdateProductCommand command) {
        AggregateLifecycle.apply(new ProductPartiallyUpdatedEvent(command.getProductId(), command.getUpdates()));
    }

    @EventSourcingHandler
    public void on(ProductPartiallyUpdatedEvent event) {
        if (event.getUpdates().containsKey("name")) {
            this.name = (String) event.getUpdates().get("name");
        }
        if (event.getUpdates().containsKey("price")) {
            this.price = (BigDecimal) event.getUpdates().get("price");
        }
        if (event.getUpdates().containsKey("quantity")) {
            this.quantity = (Integer) event.getUpdates().get("quantity");
        }
    }
}
























