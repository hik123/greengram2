package com.green.greengram2.feed.model;

import lombok.Data;

@Data
public class FeedPicsDelDto {
    private int ifeed;

    public FeedPicsDelDto(FeedDelDto dto) {
        this.ifeed = dto.getIfeed();
    }
}
