package com.green.greengram2.feed;


import com.green.greengram2.ResVo;
import com.green.greengram2.feed.model.FeedInsDto;
import com.green.greengram2.feed.model.FeedInsProcDto;
import com.green.greengram2.feed.model.FeedSelDto;
import com.green.greengram2.feed.model.FeedSelVo;
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
        }
        return list;
    }

}
