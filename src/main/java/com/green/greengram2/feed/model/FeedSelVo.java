package com.green.greengram2.feed.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedSelVo {
    private int ifeed;
    private String contents;
    private String location;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
    private int isFav; //1:좋아요, 0:아님
    private List<String> pics;
}
