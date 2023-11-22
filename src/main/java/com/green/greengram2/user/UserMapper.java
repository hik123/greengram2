package com.green.greengram2.user;

import com.green.greengram2.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignupProcDto vo);
    UserSigninProcVo selUserForSignin(UserSigninDto dto);
    UserInfoVo selFeedFavNum(int iuser);
    int feedProfileUpdate(UserPatchPicDto dto);
}
