package com.lq.pwd.controller;


import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.pwd.common.Constant;
import com.lq.pwd.dto.AddUsrDTO;
import com.lq.pwd.model.Usr;
import com.lq.pwd.model.redis.InviteCode;
import com.lq.pwd.service.impl.UsrServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-06-13
 */
@Slf4j
@RestController
@RequestMapping("/pwd/usr")
public class UsrController {
    @Value("${invite_code_use_num}")
    Integer inviteCodeMaxCount;
    @Value("${inviteCode.usefulCodes}")
    String inviteCodes;
    @Autowired
    UsrServiceImpl usrService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;


    @Autowired
    TimedCache<String, String> timedCache;
    static Map<String, String> regMap = new HashMap<>();

    @PostMapping("add")
    public R addUsr(@RequestBody @Valid AddUsrDTO usrDTO, BindingResult result){
        if (result.hasFieldErrors()){
            Map<String,Map> errData = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                String field  = fieldError.getField();
                String errMsg = fieldError.getDefaultMessage();
                String code = errMsg.split("-")[0];
                String msg = errMsg.split("-")[1];
                Map<String,String> map = new HashMap<>();
                map.put("code",code);
                map.put("msg",msg);
                errData.put(field,map);
                log.info(fieldError.getCode());
            }
            return R.ok(errData);
//            throw new RuntimeException(result.getFieldError().getDefaultMessage());
        }

//        String inviteCodes = redisTemplate.opsForValue().get("inviteCode:usefulCodes");
        log.info("缓存：{}， 请求：{}",inviteCodes,usrDTO.getInviteCode());
        if (inviteCodes==null) throw new RuntimeException(Constant.ERR_SYS);
        if (inviteCodes.indexOf(usrDTO.getInviteCode())==-1) throw new RuntimeException(Constant.ERR_INVITE_CODE_NOT_EXIT);
        String inviteCodeInfo = regMap.get("inviteCode:"+usrDTO.getInviteCode());
        InviteCode inviteCodeObj;
        if (inviteCodeInfo == null){
            inviteCodeObj = new InviteCode();
            inviteCodeObj.setCount(1);
        } else {
            inviteCodeObj = JSON.parseObject(inviteCodeInfo, InviteCode.class);
            Integer count = inviteCodeObj.getCount();
            if (count.intValue()>inviteCodeMaxCount) throw new RuntimeException(Constant.ERR_INVITE_CODE_LIMIT);
            inviteCodeObj.setCount(++count);
        }
        inviteCodeObj.getUsrList().add(usrDTO.getName());


        String regKey = "reg:"+usrDTO.getName();
        String usrName = timedCache.get(regKey);
        if (StrUtil.isEmpty(usrName)){
            timedCache.put(regKey,"1",20000);
        }else {
            throw new RuntimeException(Constant.ERR_URE_EXIT);
        }

        List<Usr> usrList = usrService.lambdaQuery().eq(Usr::getName,usrDTO.getName()).list();
        if (usrList.size()>0) throw new RuntimeException(Constant.ERR_URE_EXIT);


        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = "{bcrypt}"+encoder.encode(usrDTO.getPwd());
        usrService.save(Usr.builder().name(usrDTO.getName()).password(encodePassword).inviteCode(usrDTO.getInviteCode()).build());
        // 没有异常，将inviteCode 注册信息更新缓存
        timedCache.put("inviteCode:"+usrDTO.getInviteCode(),JSON.toJSONString(inviteCodeObj));
        return R.ok(1);



    }

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("1111");
        System.out.println(encodePassword);

        Map mm = new HashMap();
        mm.put("wk","hehe");
        System.out.println("hehe".equals(mm.get("wk")));
        Object oe = "sldjf";
        System.out.println(oe.getClass().getName());
    }

}
