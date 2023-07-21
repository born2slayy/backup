package com.rendi.RendiBackend.product.service;

import com.rendi.RendiBackend.brand.domain.Brand;
import com.rendi.RendiBackend.brand.exception.BrandErrorCode;
import com.rendi.RendiBackend.brand.exception.BrandException;
import com.rendi.RendiBackend.member.exception.MemberErrorCode;
import com.rendi.RendiBackend.member.exception.MemberException;
import com.rendi.RendiBackend.member.service.MemberService;
import com.rendi.RendiBackend.product.domain.Product;
import com.rendi.RendiBackend.product.domain.ProductImg;
import com.rendi.RendiBackend.product.dto.*;
import com.rendi.RendiBackend.product.exception.ProductErrorCode;
import com.rendi.RendiBackend.product.exception.ProductException;
import com.rendi.RendiBackend.brand.repository.BrandRepository;
import com.rendi.RendiBackend.product.repository.ProductImgRepository;
import com.rendi.RendiBackend.product.repository.ProductRepository;
import com.rendi.RendiBackend.wish.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;
    private final WishService wishService;
    private final MemberService memberService;
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Transactional
    public void autoCreateProducts(List<ProductSaveRequest> requestList){
        for (ProductSaveRequest request : requestList) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(()-> new BrandException(BrandErrorCode.BRAND_NOT_FOUND_BY_ID));
            if(productRepository.findByTitle(request.getTitle()).isPresent()){
                throw new ProductException(ProductErrorCode.PRODUCT_TITLE_DUPLICATED);
            }
            Product product = request.toProduct();
            product.setBrand(brand);
            productRepository.save(product);
        }
    }

    public void autoCreateProductImages(List<ProductImgSaveRequest> requestList){
        for(ProductImgSaveRequest request : requestList){
            logger.info("체크포인트");
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND_BY_ID));
//            ProductImg productImg = new ProductImg(request.getOriginUrl(),product);
//            productImgRepository.save(productImg);
            ProductImg productImg = request.toProductImg();
            productImg.setProduct(product);
            productImgRepository.save(productImg);
        }
    }
//    public void ciderUrlstoJpg(List<ProductImgSaveRequest> requestList){
//        for(ProductImgSaveRequest request : requestList){
//            String originUrl = request.getOriginUrl();
//            Product product = productRepository.findById(request.getProductId())
//                    .orElseThrow(()->new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND_BY_ID));
//            String storeUrl = "images/#cider" + product.getTitle() +
//        }
//        String Url =
//        String imagePath = "ciderImages/이름.jpg"+;
//        URL imageUrl = new URL(imageLink);
//        InputStream inputStream = imageUrl.openStream();
//        OutputStream outputStream = new FileOutputStream("이미지_경로/이름.jpg");
//
//        byte[] buffer = new byte[2048];
//        int length;
//        while ((length = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, length);
//        }
//
//        inputStream.close();
//        outputStream.close();
//    }

    @Transactional
    public void updateHits(HitsUpdateRequest request){
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND_BY_ID));
        product.setHits(request.getHits());
//        productRepository.save(product);
    }

// TODO :  ProductNewResponse
    @Transactional
    public List<ProductNewResponse> getNewProducts(){
        List<ProductNewResponse> dtos = new ArrayList<>();
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        for (Product product : products) {
//            Brand brand = brandRepository.findById(product.getBrand().getId()).
//                    orElseThrow(()-> new BrandException(BrandErrorCode.BRAND_NOT_FOUND_BY_PRODUCTID));
            dtos.add(new ProductNewResponse(product.getId(), product.getPrice(), product.getBrand().getId(), product.getTitle()
                    ,product.getProductImgList().stream().map(ProductImg::getStoreUrl).collect(Collectors.toList()), product.getDetailUrl()));
        }
        return dtos;
    }
    @Transactional
    public List<ProductAllResponse> getAllProducts(){
        List<ProductAllResponse> dtos = new ArrayList<>();
        // Todo : 무슨 기준으로 ALL?
        List<Product> products = productRepository.findAll();
        Long memberId = memberService.findCurrentMember().getId();
        for (Product product : products) {
            boolean wishYN = wishService.checkWishes(memberId, product.getId());
            dtos.add(new ProductAllResponse(product.getId(), product.getPrice(), product.getBrand().getId(), product.getTitle()
                    ,wishYN,product.getProductImgList().stream().map(ProductImg::getStoreUrl).collect(Collectors.toList()), product.getDetailUrl()));
        }
        return dtos;
    }

    public List<ProductAllResponse> getRecentProducts(List<Long> productIds){
        List<ProductAllResponse> dtos = new ArrayList<>();
        Long memberId = memberService.findCurrentMember().getId();
        for(Long productId : productIds){
            Product product = productRepository.findById(productId)
                            .orElseThrow(()-> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND_BY_ID));
            boolean wishYN = wishService.checkWishes(memberId, productId);
            dtos.add(new ProductAllResponse(productId, product.getPrice(), product.getBrand().getId(), product.getTitle()
            ,wishYN, product.getProductImgList().stream().map(ProductImg::getStoreUrl).collect(Collectors.toList()), product.getDetailUrl()));
        }
        return dtos;
    }



}
