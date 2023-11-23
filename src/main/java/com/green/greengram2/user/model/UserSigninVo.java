package com.green.greengram2.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSigninVo {
    private int result; //1: 성공, 2: 아이디 없음, 3: 비밀번호 틀림
    private int iuser;
    private String nm;
    private String pic;
}
