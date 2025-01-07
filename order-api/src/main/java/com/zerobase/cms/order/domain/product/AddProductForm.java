package com.zerobase.cms.order.domain.product;

import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductForm {
    private String name;
    private String description;
    private List<ProductItem> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductItem {
        private String name;
        private Integer price;
        private Integer count;
    }
}
