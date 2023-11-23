package com.green.greengram2.feed;

import com.green.greengram2.feed.model.FeedDelDto;
import com.green.greengram2.feed.model.FeedFavDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedFavMapper {
    int insFeedFav(FeedFavDto dto);
    int delFeedFav(FeedFavDto dto);

    int delFeedFav(FeedDelDto dto);

}
