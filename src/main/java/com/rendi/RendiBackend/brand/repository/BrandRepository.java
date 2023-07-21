package com.rendi.RendiBackend.brand.repository;

import com.rendi.RendiBackend.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
