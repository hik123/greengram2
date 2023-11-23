package com.green.greengram2.user;

import com.green.greengram2.ResVo;
import com.green.greengram2.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    public UserSigninVo userSignin(UserSigninDto dto) {
        UserSigninProcVo savedVo = mapper.selUserForSignin(dto);

        UserSigninVo vo = new UserSigninVo();
        if(savedVo == null) { //아이디 없음
            vo.setResult(2);
            return vo;
        } else if(!BCrypt.checkpw(dto.getUpw(), savedVo.getUpw())) {
            vo.setResult(3);
            return vo;
        }
        vo.setResult(1);
        vo.setIuser(savedVo.getIuser());
        vo.setNm(savedVo.getNm());
        vo.setPic(savedVo.getPic());
        return vo;
    }

    public ResVo userSignup(UserSignupDto dto) {
        String hashedPw = BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt());
        log.info("hashedPw : {}", hashedPw);
        UserSignupProcDto pDto = UserSignupProcDto.builder()
                .uid(dto.getUid())
                .upw(hashedPw)
                .nm(dto.getNm())
                .pic(dto.getPic())
                .build();
        int affectedRows = mapper.insUser(pDto);
        if(affectedRows == 0) {
            return new ResVo(0);
        }
        return new ResVo(pDto.getIuser());
    }

    public UserInfoVo getUserInfo(int targetIuser) {
        return mapper.selUserInfo(targetIuser);
    }

    public ResVo patchUserPic(UserPatchPicDto dto) {
        int affective = mapper.patchUserPic(dto);
        return new ResVo(affective);
    }
}
