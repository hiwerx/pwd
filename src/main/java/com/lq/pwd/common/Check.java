package com.lq.pwd.common;

import cn.hutool.core.util.StrUtil;
import com.lq.pwd.model.Acct;
import com.lq.pwd.model.AuthProduct;
import com.lq.pwd.model.Pwd;
import com.lq.pwd.service.impl.AcctServiceImpl;
import com.lq.pwd.service.impl.AuthProductServiceImpl;
import com.lq.pwd.service.impl.PwdServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Check {

    private static AuthProductServiceImpl  authProductService;
    private static AcctServiceImpl acctService;
    private static PwdServiceImpl pwdService;
    private static Integer acctMax;
    public static Integer pwdMax;
    @Autowired
    AuthProductServiceImpl authProductService1;
    @Autowired
    AcctServiceImpl acctService1;
    @Autowired
    PwdServiceImpl pwdService1;
    @Value("${personal.acct.sum}")
    Integer acctMax1;
    @Value("${personal.pwd.sum}")
    Integer pwdMax1;
    @PostConstruct
    public void init(){
        authProductService = authProductService1;
        acctService = acctService1;
        pwdService = pwdService1;
        acctMax = acctMax1;
        pwdMax = pwdMax1;
    }
    /**
     * 校验请求参数
     * @param result
     */
    public static void checkDTO(BindingResult result){
        if (result.hasFieldErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info(fieldError.getDefaultMessage());
            }
            throw new RuntimeException(result.getFieldError().getDefaultMessage());
        }
    }

    /**
     * 存在抛出7009异常。
     * @param acctName
     * @param usrId
     * @param companyId
     */
    public static void checkAcctNameExit(String acctName, Integer usrId, Integer companyId) {
        List<Acct> acctList = acctService.lambdaQuery().eq(Acct::getUsrId,usrId)
                .eq(Acct::getCompanyId, companyId)
                .eq(Acct::getValid,"1")
                .eq(Acct::getAcctName,acctName).list();
        if (acctList.size()>0) throw new RuntimeException(Constant.ERR_ACCT_NAME_EXITS);
    }


    /**
     * 判断账号密码保存上限
     * @param usrId
     */
    public static void checkAcctPwdLimit(Integer usrId){
        List<Acct> acctList = acctService.lambdaQuery().eq(Acct::getUsrId,usrId).list();
        if (acctList.size()==0)return;
        if (acctList.size()>acctMax.intValue()) throw new RuntimeException(Constant.ERR_ACCT_TOO_MUCH);
        List<Pwd> pwdList = pwdService.lambdaQuery().in(Pwd::getAcctId,acctList.stream().map(Acct::getId).collect(Collectors.toList())).list();
        if (pwdList.size()>pwdMax.intValue()) throw new RuntimeException(Constant.ERR_PED_TOO_MUCH);
    }

    public static Pwd checkAndNewPwd(String pwdType, String pwdName, Integer authProductId) {

        Pwd pwd = Pwd.builder().pwdType(pwdType).build();
        // 如果是授权登录，判断授权登录产品类型
        if (Constant.PWD_TYPE_AUTH.equals(pwdType)){
            if (authProductId==null) throw new RuntimeException(Constant.ERR_AUTH_PRODUCT_ID_NULL);
            boolean authProductExitFlag = false;
            for (AuthProduct authProduct : authProductService.lambdaQuery().list()) {
                if (authProductId.intValue()==authProduct.getId().intValue()){
                    authProductExitFlag = true;
                    break;
                }
            }
            if (!authProductExitFlag) throw new RuntimeException(Constant.ERR_AUTH_PRODUCT_NOT_EXIT);
            pwd.setAuthProductId(authProductId);
        }
        pwd.setPwd(pwdName);

        return pwd;
    }

    public static void checkPwdTypeByCompanyType(String pwdType, String companyType) {
        try {
            if (!Constant.pwdTypeGroupByCompanyTypeMap.get(companyType).contains(pwdType))
                throw new RuntimeException(Constant.ERR_PWD_TYPE);
        }catch (Exception e){
            throw new RuntimeException(Constant.ERR_PWD_TYPE);
        }

    }



    public static void checkAcctName(String acctName){
        if (StrUtil.isBlank(acctName))
            throw new RuntimeException(Constant.ERR_ACCT_NAME_NULL);
        if (acctName.length()>30){
            throw new RuntimeException(Constant.ERR_ACCT_NAME_TOO_LONG);
        }
    }

    public static void checkProductByCompanyType(Integer productId, String companyType) {
        try {
            if (productId==null) throw new RuntimeException(Constant.ERR_PRODUCT_ID_NULL);
            if (!Constant.productIdGroupByCompanyTypeMap.get(companyType).contains(productId))
                throw new RuntimeException(Constant.ERR_PRODUCT_ID);
        }catch (Exception e){
            throw new RuntimeException(Constant.ERR_PRODUCT_ID);
        }
    }
}
