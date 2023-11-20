package com.green.greengram2.user;

import com.green.greengram2.user.model.UserSigninDto;
import com.green.greengram2.user.model.UserSigninProcVo;
import com.green.greengram2.user.model.UserSignupProcDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignupProcDto vo);
    UserSigninProcVo selUserForSignin(UserSigninDto dto);
}
