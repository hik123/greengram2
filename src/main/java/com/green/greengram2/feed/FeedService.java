package com.green.greengram2.feed;


import com.green.greengram2.ResVo;
import com.green.greengram2.feed.model.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commMapper;

    public ResVo postFeed(FeedInsDto dto) {
        if(dto.getPics().size() == 0) {
            return new ResVo(2); //사진이 하나도 없음
        }
        FeedInsProcDto pDto = FeedInsProcDto.builder()
                .iuser(dto.getIuser())
                .contents(dto.getContents())
                .location(dto.getLocation())
                .pics(dto.getPics())
                .build();
        int feedAffectedRows = mapper.insFeed(pDto);
        if(feedAffectedRows == 0 || pDto.getIfeed() == 0) { return new ResVo(0); }
        int feedPicsAffectedRows = picsMapper.insFeedPics(pDto);
        if(feedPicsAffectedRows != dto.getPics().size()) { //트랜잭션이면 rollback
            return new ResVo(3); //사진 등록이 제대로 안 됨.
        }
        return new ResVo(pDto.getIfeed());
    }

    //N+1 허용
    public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
        List<FeedSelVo> list = mapper.selFeedAll(dto);

        for(FeedSelVo vo : list) {
            List<String> pics = picsMapper.selFeedPicsAll(vo.getIfeed());
            vo.setPics(pics);

            List<FeedCommentSelVo> comments = commMapper.selCommentAll(FeedCommentSelDto.builder()
                    .ifeed(vo.getIfeed())
                    .startIdx(0)
                    .rowCount(4)
                    .build());
            if(comments.size() == 4) {
                vo.setIsMoreComment(1);
                comments.remove(comments.size() - 1);
            }
            vo.setComments(comments);

        }
        return list;
    }


    public ResVo toggleFav (FeedFavDto dto) {           //연산 작업 service에서
        int affectedRow = favMapper.delFav(dto);
        if(affectedRow == 1) {
            return new ResVo(0); // delete 됐을때 0 리턴
        } else if(affectedRow == 0) {
            favMapper.insFav(dto);
            return new ResVo(1); // insert 됐을때 1 리턴
        }
        return null;
    }

    //ResVo result 값이 insert일때 1 , delete일때 2
    //없으면 insert 있으면 삭제

    public ResVo insFeedComment(FeedCommentInsDto dto) {
        try {
            int affectedRow = commMapper.insFeedComment(dto);
            return new ResVo(affectedRow);
        }
        catch (Exception e) {
            return new ResVo(0);
        }
    }
}
