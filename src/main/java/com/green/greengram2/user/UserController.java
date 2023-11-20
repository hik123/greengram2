package com.green.greengram2.user;

import com.green.greengram2.ResVo;
import com.green.greengram2.user.model.UserSigninDto;
import com.green.greengram2.user.model.UserSigninVo;
import com.green.greengram2.user.model.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

// (post) http://localghost:8082/api/user/signup
@Slf4j //spring framework log4j  logback / log멤버필드 생성됨
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    public final UserService service;

    @PostMapping("/signin")
    public UserSigninVo postUserSignin(@RequestBody UserSigninDto dto) {
        log.info("signin - dto: {}", dto);
        return service.userSignin(dto);
    }

    @PostMapping("/signup")
    public ResVo postUserSignup(@RequestBody UserSignupDto dto) {
        log.info("dto: {}", dto);       // @Slf4j기능,  타입지정 안해도됨
        return service.userSignup(dto);  //ResVo객체에 insert한 레코드 pk값을 담아서 응답처리
    }
}
