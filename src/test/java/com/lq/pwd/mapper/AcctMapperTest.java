package com.lq.pwd.mapper;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.lq.pwd.vo.AcctPwdVO;
import com.lq.pwd.vo.CompanyAcctPwdVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AcctMapperTest {
    @Autowired
    AcctMapper acctMapper;

    //@Test
    public void getAllPwd(){
        List<CompanyAcctPwdVO> res  = acctMapper.getAllPwd(1,null);
        Console.log(JSON.toJSONString(res,true));

        res  = acctMapper.getAllPwd(1,"%交通%");
        Console.log(JSON.toJSONString(res,true));

        res  = acctMapper.getAllPwd(1,"%浦发%");

        Console.log("{},{}",res==null , JSON.toJSONString(res,true));
    }

   // @Test
    public void selectBankAppPwdCountTest(){
       Console.log( acctMapper.selectBankAppPwdCount(4,1,"0"));;
        Console.log( acctMapper.selectBankAppPwdCount(4,1,"3"));;

    }

    //@Test
    public void selectBankAcctIdByPwdTypeTest(){
        List<Integer> acctIdList = acctMapper.selectBankAcctIdByPwdType(8, 1);
        System.out.println(acctIdList);
    }
}
