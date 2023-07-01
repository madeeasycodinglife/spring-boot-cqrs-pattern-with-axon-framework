package com.madeeasy.commandapi.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Map;

@Data
@Builder
public class PartialUpdateProductCommand {
    @TargetAggregateIdentifier
    private String productId;
    private Map<String, Object> updates;
}
