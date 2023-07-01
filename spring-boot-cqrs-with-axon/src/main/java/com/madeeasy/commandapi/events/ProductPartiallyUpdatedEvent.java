package com.madeeasy.commandapi.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPartiallyUpdatedEvent {
    private String productId;
    private Map<String, Object> updates;
}
