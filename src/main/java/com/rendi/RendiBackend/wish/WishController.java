package com.rendi.RendiBackend.wish;

import com.rendi.RendiBackend.common.dto.StringResponse;
import com.rendi.RendiBackend.member.domain.Member;
import com.rendi.RendiBackend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishController {
    private final WishService wishService;
    private final MemberService memberService;
//    @PostMapping("")
//    public StringResponse saveWishes(WishSaveRequest request){
//        Long id = wishService.saveWishes(request).getId();
//        return new StringResponse("상품 찜하기 설정이 완료되었습니다." + id);
//    }

    @PostMapping("/{productId}")  //    찜 유무 판단(이미 찜 되어있으면 T하고 삭제, 안되어있으면 F하고 생성)
    public boolean wishYorN(@PathVariable Long productId){
        return wishService.inWishListTF(productId);
    }
    @GetMapping("/all")
    public List<WishListResponse> readWishList(){
        return wishService.readWishes();
    }
}
