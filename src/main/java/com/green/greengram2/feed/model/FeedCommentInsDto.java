package com.green.greengram2.feed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FeedCommentInsDto {
    private int ifeed;
    private int iuser;
    private String comment;
}
