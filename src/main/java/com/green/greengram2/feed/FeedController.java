package com.green.greengram2.feed;


import com.green.greengram2.ResVo;
import com.green.greengram2.feed.model.*;
import com.green.greengram2.user.model.UserInfoVo;
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


    @Operation(summary = "피드 리스트", description = "전체 피드 리스트, 특정 사용자 프로필 화면에서 사용할 피드 리스트")
    @Parameters(value = {
              @Parameter(name="page", description = "페이징")
            , @Parameter(name="loginedIuser", description = "loginedIuser")
            , @Parameter(name="targetIuser", description = "targetIuser")
    })
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


    @Operation(summary = "좋아요 처리", description = "Toggle로 처리함")
    @Parameters(value = {
            @Parameter(name = "ifeed", description = "feed pk")
            , @Parameter(name = "iuser", description = "로그인한 유저 pk")
    })

    @GetMapping("/fav")  // 쿼리스트링 사용 >> ///localhost:8082/api/feed/fav?ifeed=6&iuser=9
    public ResVo toggleFav(FeedFavDto dto) {
        log.info("dto: {}", dto);
        return service.toggleFav(dto);
        //ResVo result 값이 insert일때 1 , delete일때 0
        //없으면 insert 있으면 삭제
    }

    @PostMapping("/comment")
    public ResVo insFeedComment (@RequestBody FeedCommentInsDto dto){
        return service.insFeedComment(dto);
    }


}
