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
    public final UserMapper mapper;

    public UserSigninVo userSignin(UserSigninDto dto) {
        UserSigninProcVo savedVo = mapper.selUserForSignin(dto);

        UserSigninVo vo = new UserSigninVo();
        if(savedVo == null) {
            vo.setResult(2);
            return vo;
        } else if(!BCrypt.checkpw(dto.getUpw(), savedVo.getUpw())) {
            vo.setResult(3); //checkpw(password, hashedPassword),boolean 타입으로 비밀번호와 암호화된 비밀번호를 인자로 받아 같을 경우 true, 다를 경우 false를 반환한다.
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
}

    /*
    public ResVo userSignup(UserSignupDto dto) {
        String hashedPw = BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt());
        log.info("hashedPw : ", hashedPw);
        dto.setUpw(hashedPw);


        //UserSignupVo pDto = UserSignupVo.builder()
                .uid(dto.getUid())
                .upw(hashedPw)
                .nm(dto.getNm())
                .pic(dto.getPic())
                .build();
        //return ;


        UserSignupProcDto vo = new UserSignupProcDto(dto);
        mapper.userSignup(vo);
        ResVo rv = new ResVo(vo.getIuser());
        return rv;
    }
     */

