package com.green.greengram2.feed;

import com.green.greengram2.feed.model.FeedInsDto;
import com.green.greengram2.feed.model.FeedInsProcDto;
import com.green.greengram2.feed.model.FeedSelDto;
import com.green.greengram2.feed.model.FeedSelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedInsProcDto dto);
    List<FeedSelVo> selFeedAll(FeedSelDto dto);
}
