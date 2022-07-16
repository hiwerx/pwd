package com.lq.pwd.service;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.lq.pwd.dto.AcctPwdDTO;
import com.lq.pwd.model.Pwd;
import com.lq.pwd.service.impl.AcctServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AcctServiceImplTest {
    @Autowired
    AcctServiceImpl acctServicei;

    //@Test
    public void addAcctTest(){
        acctServicei.addAcct(AcctPwdDTO.builder()
                .acctName("17191099174")
//                .acctType("0")
                .phone("17191099174")
//                .productName("微信")
//                .usrId(1)
                .companyId(4)
                .pwd("19991")
                .pwdType("0")
                .build());
    }

    //@Test
    public void deleteAcctId(){
        acctServicei.deleteAcctById(1,1);
    }

    //@Test
    public void updatePwdTest(){
        acctServicei.updatePwd(AcctPwdDTO.builder()
//                .acctId(1)
                .pwd("9999").pwdType("1").build());
    }

    //@Test
    public void hisPwdTest(){
        List<Pwd> res = acctServicei.hisPwd(1);
        Console.log(JSON.toJSONString(res,true));
    }
}
