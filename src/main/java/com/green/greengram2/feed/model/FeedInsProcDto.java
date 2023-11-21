package com.green.greengram2.feed.model;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
public class FeedInsProcDto {
    private int ifeed;
    private int iuser;
    private String contents;
    private String location;
    private List<String> pics;
}
