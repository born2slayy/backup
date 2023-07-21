package com.rendi.RendiBackend.brand.service;

import com.rendi.RendiBackend.brand.domain.Brand;
import com.rendi.RendiBackend.brand.dto.BrandSaveRequest;
import com.rendi.RendiBackend.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    @Transactional
    public Brand createBrand(BrandSaveRequest request) {
        return brandRepository.save(new Brand(request.getBrandName(), request.getIconUrl(), request.getBannerUrl()));
    }
}
