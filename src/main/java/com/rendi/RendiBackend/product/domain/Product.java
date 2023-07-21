package com.rendi.RendiBackend.product.domain;

import com.rendi.RendiBackend.brand.domain.Brand;
import com.rendi.RendiBackend.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String title;
    private String price;
    private String detailUrl;
    private Long hits;
//    private String wishYN;
    private String colour;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Transient
    @OneToMany(mappedBy = "product")
    private List<ProductImg> productImgList = new ArrayList<>();




}