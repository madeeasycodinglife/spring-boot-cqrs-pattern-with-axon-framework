package com.madeeasy.queryapi.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindProductByIdQuery {
    private String productId;
}
