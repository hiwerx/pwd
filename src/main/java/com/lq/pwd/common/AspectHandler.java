package com.lq.pwd.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lq.pwd.dto.AcctPwdDTO;
import com.lq.pwd.security.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Aspect
@Component
@Order(1)
@Slf4j
public class AspectHandler {

    @Before("execution(* com.lq.pwd.controller.AcctController.add(..))")
    public void initData(JoinPoint pjp) throws Throwable {
//        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDisFiX3Ez+UaRnWCj5OuXyxC/uafJCH/yMm5Nl/ku+qlpo5Dl+HuMQ55KVZBoMpDrEfeYrexo9rI3CgNNOJX2nh5QbuICQZ7T722+CD7WA7dV+VFh9Y4WtNpys2QzenpyryeInnU/ip+BLy1FUINISGSmjz75TM6taUtHFTdwzGwIDAQAB";
//        Object[] args = pjp.getArgs();
//        for (Object arg : args) {
//            if (arg instanceof AcctPwdDTO)Console.log(JSON.toJSONString(arg));
//            if (arg instanceof LoginUserInfo)Console.log(JSON.toJSONString(arg));
//        }
//        int index = -1;
//        String data = "";
//        String signature = "";
//        for (int i = 0; i < args.length; i++) {
//            Object arg = args[i];
//            if (arg instanceof AcctPwdDTO) {
//                index = i;
//            }
//            if (arg instanceof JSONObject){
//                arg = (JSONObject)arg;
//                data= ((JSONObject) arg).getString("data");
//                signature = ((JSONObject) arg).getString("signature");
//
//            }
//        }
//        if (StrUtil.isNotEmpty(data)&&StrUtil.isNotEmpty(signature)&&Rsa.verify(pubKey,data,signature)) {
            log.info("验签成功");
//            AcctPwdDTO acctPwdDTO = getData(data);
//            args[index] = acctPwdDTO;
//        }

//        ((ProceedingJoinPoint)pjp).proceed(args);
    }

    public static AcctPwdDTO getData(String data){
        String jsonData = URLDecoder.decode(Base64.decodeStr(data,StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        AcctPwdDTO acctPwdDTO = JSON.parseObject(jsonData,AcctPwdDTO.class);
        return acctPwdDTO;
    }

    public static void main(String[] args) {
        AcctPwdDTO acctPwdDTO =getData("JTdCJTIyY29tcGFueUlkVHlwZSUyMiUzQW51bGwlMkMlMjJhY2N0TmFtZSUyMiUzQW51bGwlMkMlMjJwd2RUeXBlJTIyJTNBbnVsbCUyQyUyMnB3ZCUyMiUzQW51bGwlMkMlMjJhdXRoUHJvZHVjdElkJTIyJTNBbnVsbCUyQyUyMnBob25lJTIyJTNBJTIybGtzamZsa3NqZmxhZ2xrJUU1JTg1JThCJUU4JThFJUIxJUU2JTk2JUFGJUU1JThCJTkyJUU3JUE5JUJBJUU5JTk3JUI0JTIyJTJDJTIycHJvZHVjdElkJTIyJTNBbnVsbCUyQyUyMmVtYWlsJTIyJTNBJTIyc2RrZmpsZHNnamZsZ2phJTIyJTdE");
        Console.log(JSON.toJSONString(acctPwdDTO));
    }
}
