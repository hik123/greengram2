package com.green.greengram2.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter

public class UserSignupProcDto {
    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private String pic;

    /*
    public UserSignupProcDto(UserSignupDto dto) {
        this.uid = dto.getUid();
        this.upw = dto.getUpw();
        this.nm = dto.getNm();
        this.pic = dto.getPic();
    }

     */
}
