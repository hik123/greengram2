package com.green.greengram2.user;

import com.green.greengram2.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignupProcDto dto);
    UserSigninProcVo selUserForSignin(UserSigninDto dto);
    UserInfoVo selUserInfo(int targetIuser);
    int patchUserPic(UserPatchPicDto dto);
}
