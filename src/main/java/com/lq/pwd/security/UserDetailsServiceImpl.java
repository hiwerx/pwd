package com.lq.pwd.security;

import com.lq.pwd.model.Usr;
import com.lq.pwd.service.impl.UsrServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${usr.account.valid.hour}")
    long hour;
    @Autowired
    UsrServiceImpl userService;
    //更改登录用户为手机，所以先根据手机号查询用户信息是否存在；
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usr usr = userService.lambdaQuery().eq(Usr::getName,s).one();
        if (s == null){
            return null;
        }
        LocalDateTime regTime = usr.getCreateTime();
        boolean validUsr = regTime.plusHours(hour).isAfter(LocalDateTime.now());
        log.info("账户有效性：{}",validUsr);
        List<String> permissions = Arrays.asList("w","k");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission);
            authorities.add(grantedAuthority);
        }
        if("0000".equals(usr.getInviteCode()))validUsr=true;
        LoginUserInfo userInfo = new LoginUserInfo(s,usr.getPassword(),validUsr,true,true,true,authorities);
        userInfo.setUserId(usr.getId());
        return userInfo;
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodePassword = encoder.encode("1111");
//        System.out.println(encodePassword);
//        if ("root".equals(s)){
//            return User.builder()
//                    .username("root")
//                    .password("{bcrypt}"+encodePassword)
//                    .accountLocked(true)
//                    .authorities("hh")
//                    .build();
//        }
//        return null;
//        List<PermissionVO> permissions = userLoginVO.getPermissions();
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (PermissionVO permission : permissions) {
//            String authority = permission.getAuthority();
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
//            authorities.add(grantedAuthority);
//        }
//
//
//        LoginUserInfo userInfo  = new LoginUserInfo(
//                s,
//                userLoginVO.getPassword(),
//                userLoginVO.getIsEnabled() == 1,
//                true,
//                true,
//                userLoginVO.getIsLocked() == 0,
//                authorities
//        );
//
//        userInfo.setUserId(userLoginVO.getId());
//        userInfo.setUserNickName(userLoginVO.getNickName());

//        return userInfo;


    }
}

