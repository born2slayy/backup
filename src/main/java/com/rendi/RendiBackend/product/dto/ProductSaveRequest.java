package com.rendi.RendiBackend.product.dto;

import com.rendi.RendiBackend.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaveRequest {
    private Long brandId;
    private String title; //product name
    private String price;
    private String detailUrl;
    private Long hits;
    private String colour;

    public Product toProduct() {
        return Product.builder()
                .title(title)
                .price(price)
                .detailUrl(detailUrl)
                .hits(hits)
                .colour(colour)
                .build();
    }

}
