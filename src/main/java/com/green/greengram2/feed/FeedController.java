package com.green.greengram2.feed;


import com.green.greengram2.ResVo;
import com.green.greengram2.feed.model.FeedInsDto;
import com.green.greengram2.feed.model.FeedInsProcDto;
import com.green.greengram2.feed.model.FeedSelDto;
import com.green.greengram2.feed.model.FeedSelVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@Tag(name = "피드 API", description = "피드 관련 처리")
public class FeedController {
    private final FeedService service;

    @Operation(summary = "피드 등록", description = "피드 등록 처리")
    @Parameters(value = {
            @Parameter(name="iuser", description = "작성자pk")
            , @Parameter(name="contents", description = "내용")
            , @Parameter(name="location", description = "위치")
            , @Parameter(name="pics", description = "사진")
    })
    @PostMapping
    public ResVo postFeed(@RequestBody FeedInsDto dto) {
        log.info("dto : {}", dto);
        return service.postFeed(dto);
    }

    @GetMapping
    public List<FeedSelVo> getFeedAll(int page, int loginedIuser
            , @RequestParam(required=false, defaultValue="0") int targetIuser) {
        log.info("targetIuser : {}", targetIuser);
        final int ROW_COUNT = 30;

        return service.getFeedAll(FeedSelDto.builder()
                .loginedIuser(loginedIuser)
                .targetIuser(targetIuser)
                .startIdx((page-1) * ROW_COUNT)
                .rowCount(ROW_COUNT)
                .build());
    }
}
