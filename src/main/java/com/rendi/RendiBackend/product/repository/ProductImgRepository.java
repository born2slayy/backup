package com.rendi.RendiBackend.product.repository;

import com.rendi.RendiBackend.product.domain.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
}
