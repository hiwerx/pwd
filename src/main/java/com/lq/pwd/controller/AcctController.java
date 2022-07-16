package com.lq.pwd.controller;


import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.lq.pwd.common.Check;
import com.lq.pwd.common.Constant;
import com.lq.pwd.common.Rsa;
import com.lq.pwd.dto.AcctPwdDTO;
import com.lq.pwd.dto.AddPwdDTO;
import com.lq.pwd.dto.EditAcctDTO;
import com.lq.pwd.dto.EditPwdDTO;
import com.lq.pwd.security.LoginUserInfo;
import com.lq.pwd.service.impl.AcctServiceImpl;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 账号信息 前端控制器
 * </p>
 *
 * @author lq.com
 * @since 2022-06-13
 */
@Slf4j
@RestController
@RequestMapping("/pwd/acct")
public class AcctController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    public static String priKeyH = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJvJqEQox8eSW6yAtn1fI6+SDZLaVbInKjWFwMX5IBjghZLkZf5srJyBrz/G+hGQG/DFN1iF9SevPBG4Jhk6SHlMV76totfenaP4HtQe3GoxicwIglUqa0duuDJb43LEj17sjvzM0TDeWSpi16Zz5zIdWc0qPzq27aDcHCjn3NzhAgMBAAECgYAJvJDHscTKtFsGbQT60PdqAbbXds3kVI5Oyg1CUk+vPlka1SuKu0AOiAxr8AOxFVCpu6m53qVz1X/rm5bF/N6KnUN8PbF9yBdx/BlBTwUWVxHpQKzuHNdA062ySwXIaJelGbhuk+GlDM/F8QIEwRjCd9NB84tqqZDZZZ2wKNoAAQJBAMsBRoVV+AlBHY+0OcXntsHZe6rjXQOhbjCy6g0PF77iNi6ViyUUnG8MWir8WcfYJFbEIRYEKGrG+ikXVmN6CQ0CQQDEdOAVcSg8COUvtsPUjl2tLEs0PAuKc/6PHOjDUyQfrE2oIOziuQWH/6QrW/B6NtS9Og+nZS78bndLk3YbwEYlAkB5UbPRb4UiErYV8YEtUsMXql+LywEFcG4n0GSrlT99pjb3NAvKBz1N6DXixpjpI7Tj3aZgP+/fkDZkZDwOixnpAkEAsKnBUMbfPY1qO8wIsj4L80xfnGtanXjNs3h1wCAl3e2eL9Db9M4ZMUEsKmmVCPIBwOBTb17IL+xOjsHedfojmQJAc9GCrwOoWJwYuKmwFR16EuGwFtKcBBoKtEbvEYFIpPGdoCjfT9LxZp/qLOb2Gh4Cv8ypilCJX9PpWtDvNGmooQ==";

    private String dePwd(String pwd){
        if ("false".equals(pwd)|| StrUtil.isBlank(pwd)) throw new RuntimeException(Constant.ERR_PWD_NULL);
        pwd = Rsa.decodeRsa(priKeyH,pwd);
        if (pwd.length()> Constant.PWD_MAX_LEN)throw new RuntimeException(Constant.ERR_ACCT_PWD_TOO_LONG);
        return pwd;
    }

    private void moreReq(LoginUserInfo userInfo,String busName){
        String key = "usr:"+userInfo.getUserId()+":"+busName;
        String value = redisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(value)) {
            redisTemplate.opsForValue().set(key, "1", 10, TimeUnit.SECONDS);
        }else{
            redisTemplate.opsForValue().set(key,(Integer.parseInt(value)+1)+"",10,TimeUnit.SECONDS);
            throw new RuntimeException(Constant.ERR_SYS_P);
        }
    }
    @Autowired
    AcctServiceImpl acctService;
    @RequestMapping("add")
    public R add(@RequestBody @Valid AcctPwdDTO dto, BindingResult result, @AuthenticationPrincipal LoginUserInfo userInfo){
        Console.log(JSON.toJSONString(dto,true));
        Check.checkDTO(result);
        moreReq(userInfo,"addAcctPwd");
        dto.setPwd(dePwd(dto.getPwd()));
        // 暂时将用户id写死
        dto.setUsrId(userInfo.getUserId());
        acctService.addAcct(dto);
        return R.ok(1);
    }

    @RequestMapping("get")
    public R get(@AuthenticationPrincipal LoginUserInfo userInfo){
        Console.log("get");
        return R.ok(acctService.getAllPwd(userInfo.getUserId()));
    }

    @RequestMapping("del/company/{companyId}")
    public R delByCompanyId(@PathVariable Integer companyId,@AuthenticationPrincipal LoginUserInfo userInfo){
        moreReq(userInfo,"delAll"+companyId);
        acctService.deleteAllAcctByCompanyId(companyId,userInfo.getUserId());
        return R.ok("删除成功");
    }

    @RequestMapping("del/acct/{acctId}")
    public R delByAcctId(@PathVariable Integer acctId,@AuthenticationPrincipal LoginUserInfo userInfo){
        moreReq(userInfo,"delAcct"+acctId);
        acctService.deleteAcctById(acctId, userInfo.getUserId());
        return R.ok("删除成功");
    }

    @RequestMapping("del/pwd/{pwdId}")
    public R delPwdByPwdId(@PathVariable Integer pwdId,@AuthenticationPrincipal LoginUserInfo userInfo){
        moreReq(userInfo,"delPwd"+pwdId);
        acctService.deletePwdById(pwdId,userInfo.getUserId());
        return R.ok("删除成功");
    }

    @RequestMapping("add/pwd")
    public R addPwd(@RequestBody @Valid AddPwdDTO addPwdDTO,BindingResult result, @AuthenticationPrincipal LoginUserInfo userInfo){
        Check.checkDTO(result);
        moreReq(userInfo,"addPwd");
        addPwdDTO.setPwd(dePwd(addPwdDTO.getPwd()));
        addPwdDTO.setUsrId(userInfo.getUserId());
        acctService.addPwd(addPwdDTO);
        return R.ok("添加成功");
    }

    @RequestMapping("acct/modify")
    public R modifyAcct(@RequestBody @Valid EditAcctDTO acctPwdDTO, BindingResult result,@AuthenticationPrincipal LoginUserInfo userInfo){
        Check.checkDTO(result);
        moreReq(userInfo,"modifyAcct"+acctPwdDTO.getAcctId());
        acctPwdDTO.setUsrId(userInfo.getUserId());
        acctService.editAcct(acctPwdDTO);
        return R.ok("修改成功");
    }

    @RequestMapping("pwd/modify")
    public R modifyPwd(@RequestBody @Valid EditPwdDTO pwdDTO,BindingResult result, @AuthenticationPrincipal LoginUserInfo userInfo){
        // 校验参数
        Check.checkDTO(result);
        moreReq(userInfo,"modifyPwd"+pwdDTO.getPwdId());
        // 解密并校验密码
        pwdDTO.setPwd(dePwd(pwdDTO.getPwd()));
        pwdDTO.setUsrId(userInfo.getUserId());
        acctService.editPwd(pwdDTO);
        return R.ok("修改成功");
    }

}
