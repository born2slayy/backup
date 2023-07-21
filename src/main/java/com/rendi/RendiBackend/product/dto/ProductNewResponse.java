package com.rendi.RendiBackend.product.dto;

import com.rendi.RendiBackend.brand.domain.Brand;
import com.rendi.RendiBackend.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductNewResponse {
    private Long productId;

    private String price;

    private Long brandId;

    private String title;

//    private boolean wishYN;

    private List<String> imgUrls;

    private String href;

//    public static List<ProductNewResponse> toNewDtoList(List<Product> entities, Brand brand, List<String> imgUrls) {
//        List<ProductNewResponse> dtos = new ArrayList<>();
//
//        for (Product entity : entities)
//            dtos.add(new ProductNewResponse(entity.getId(),entity.getPrice(), brand.getId(), entity.getTitle(), ));
//
//        return dtos;
//    }
}
