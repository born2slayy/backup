package com.rendi.RendiBackend.search;

import com.rendi.RendiBackend.common.dto.StringResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final PopularService popularService;

    @PostMapping("/keyword/update")
    public StringResponse createKeyword(@RequestBody SearchKeywordRequest request){
        popularService.incrementSearchCount(request.getKeyword());
        return new StringResponse("검색어 DB 업데이트 성공");
    }
    @GetMapping("keyword/popular")
    public List<PopularKeywordResponse> getPopular10(){
        return popularService.getPopularKeywords();
    }
}
