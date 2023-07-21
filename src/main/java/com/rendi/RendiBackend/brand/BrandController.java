package com.rendi.RendiBackend.brand;

import com.rendi.RendiBackend.brand.domain.Brand;
import com.rendi.RendiBackend.brand.dto.BrandSaveRequest;
import com.rendi.RendiBackend.brand.service.BrandService;
import com.rendi.RendiBackend.common.dto.StringResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;
    @PostMapping("add/hj")
    public StringResponse createBrand(@RequestBody BrandSaveRequest request) {
        Brand brand = brandService.createBrand(request);
        Long id = brand.getId();
        String name = brand.getBrandName();
        return new StringResponse("added new Brand. id: " + id + "brand name : " + name);
    }
}