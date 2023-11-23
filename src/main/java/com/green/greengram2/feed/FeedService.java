package com.green.greengram2.feed;

import com.green.greengram2.ResVo;
import com.green.greengram2.feed.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;

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

            //4개, 2개, 5개(2), 3개
            List<FeedCommentSelVo> comments = commentMapper.selCommentAll(FeedCommentSelDto.builder()
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

    //------------------- FeedFav
    public ResVo toggleFeedFav(FeedFavDto dto) {
        int delAffectedRows = favMapper.delFeedFav(dto);

        if(delAffectedRows == 1) {
            return new ResVo(0);
        }
        int insAffectedRows = favMapper.insFeedFav(dto);
        return new ResVo(1);
    }

    //-------------------- FeedComment
    public ResVo postComment(FeedCommentInsDto dto) {
        FeedCommentInsProcDto pDto = new FeedCommentInsProcDto(dto);
        int affectedRows = commentMapper.insComment(pDto);
        return new ResVo(pDto.getIfeedComment());
    }

    public List<FeedCommentSelVo> getCommentAll(int ifeed) {
        return commentMapper.selCommentAll(FeedCommentSelDto.builder()
                                            .ifeed(ifeed)
                                            .startIdx(3)
                                            .rowCount(9999)
                                            .build());
    }

    public ResVo delComment(FeedCommentDelDto dto) {
        int affectedRows = commentMapper.delComment(dto);
        return new ResVo(affectedRows);
    }
}
