package com.rendi.RendiBackend.product;

import com.rendi.RendiBackend.common.dto.StringResponse;
import com.rendi.RendiBackend.product.domain.Product;
import com.rendi.RendiBackend.product.dto.*;
import com.rendi.RendiBackend.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public StringResponse createProducts(@RequestBody List<ProductSaveRequest> requestList) {
        productService.autoCreateProducts(requestList);
        return new StringResponse("상품 리스트 DB 저장 완료");
    }
    @PostMapping("/images")
    public StringResponse createProductImages(@RequestBody List<ProductImgSaveRequest> requestList){
        productService.autoCreateProductImages(requestList);
        return new StringResponse("상품 이미지 리스트 DB 저장 완료");
    }
    @PatchMapping("/hits/update")
    public StringResponse updateHits(@RequestBody HitsUpdateRequest request) {
        productService.updateHits(request);
        return new StringResponse("hits changed");
    }

    // TODO: here it starts
    @GetMapping("/new")
    public List<ProductNewResponse> getProductsSortedByDate() {
        return productService.getNewProducts();
    }
    @GetMapping("/all")
    public List<ProductAllResponse> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/recent")
    public List<ProductAllResponse> getRecentProducts(@RequestBody List<Long> productIds) {
        return productService.getRecentProducts(productIds);
    }

}
