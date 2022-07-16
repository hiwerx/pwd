package com.lq.pwd.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Configuration
public class Constant {

    // 请求参数长度验证
    public static final int PHONE_MAX_LEN = 11;
    public static final int ACCT_MAX_LEN = 20;
    public static final int PWD_MAX_LEN = 20;
    public static final int EMAIL_MAX_LEN = 20;
    public static final int COMPANY_NAME_MAX_LEN = 10;
    public static final int COMPANY_URL_MAX_LEN = 30;
    public static final int COMPANY_ICON_MAX_LEN = 20;
    public static final int USR_NAME_MAX_LEN = 10;
    public static final int USR_PWD_MIN_LEN = 6;
    public static final int USR_PWD_MAX_LEN = 12;



    // 厂商类型
    public static final String COMPANY_TYPE_WEB = "0";
    public static final String COMPANY_TYPE_COMMUNICATION = "1";
    public static final String COMPANY_TYPE_BANK = "2";

    /**其他*/
    public static final String COMPANY_TYPE_OTHER = "3";
    public static final String COMPANY_TYPE_PRIVATE = "9";

    // ['登录密码','支付密码','银行卡交易密码','ukey登录密码','授权登录','短信验证码']
    /**登录密码*/
    public static final String PWD_TYPE_LOGIN = "0";
    /**  支付密码 */
    public static final String PWD_TYPE_PAY = "1";
    /**银行卡交易密码*/
    public static final String PWD_TYPE_TRADE = "2";
    /**ukey登录密码*/
    public static final String PWD_TYPE_KEY = "3";
    /**授权登录*/
    public static final String PWD_TYPE_AUTH = "4";
    /**短信验证*/
    public static final String PWD_TYPE_MSG = "5";
    /**邮箱验证*/
    public static final String PWD_TYPE_EMAIL = "6";

    // 为空
    public static final String ERR_PWD_NULL = "7000-密码不能为空";
    public static final String ERR_ACCT_NAME_NULL = "7001-账号不能为空";
    public static final String ERR_PWD_TYPE_NULL = "7002-密码类型不能为空";
    public static final String ERR_ACCT_ID_NULL = "7003-账号id不能为空";
    public static final String ERR_PWD_ID_NULL = "7004-密码id不能为空";
    public static final String ERR_ACCT_PWD_TOO_LONG = "7014-密码长度不能超过"+ PWD_MAX_LEN +"个字符";

    public static final String ERR_AUTH_PRODUCT_ID_NULL = "7005-授权登录授权登录产品不能为空";
    public static final String ERR_PHONE_NULL="7006-短信验证登录时，手机不能为空";
    public static final String ERR_PRODUCT_ID_NULL = "7007-应用的产品id不能为空，请选择";
    public static final String ERR_ACCT_NAME_TOO_LONG = "7008-账号不能超过"+ ACCT_MAX_LEN +"个字符";
    public static final String ERR_ACCT_NAME_EXITS = "7009-该公司名下此账户名已存在";
    public static final String ERR_ACCT_NAME_NOT_EXITS = "7014-该公司名下此账户名不存在";
    public static final String ERR_ACCT_ID = "7010-账户id有误";
    public static final String ERR_PHONE_TOO_LONG = "7011-手机号长度过长";
    public static final String ERR_EMAIL = "7012-邮件格式有误";
    public static final String ERR_EMAIL_TOO_LONG = "7013-邮件长度不超"+ EMAIL_MAX_LEN +"个字符";

    public static final String ERR_COMPANY_TYPE_NULL = "8000-企业类型为空，必填";
    public static final String ERR_COMPANY_NAME_NULL = "8001-企业名称为空，必填";
    public static final String ERR_COMPANY_EXIT = "8002-该企业已添加过，无需添加";
    public static final String ERR_COMPANY_NUM_FULL = "8003-企业添加数据已达上限：";
    public static final String ERR_COMPANY_NAME_MAX_LEN = "8004-企业名称最多"+ COMPANY_NAME_MAX_LEN +"个字符";
    public static final String ERR_COMPANY_URL_MAX_LEN = "8005-企业url地址最长"+ COMPANY_URL_MAX_LEN +"个字符";
    public static final String ERR_COMPANY_PHONE_MAX_LEN = "8006-联系电话最长"+ PHONE_MAX_LEN +"个字符";
    public static final String ERR_COMPANY_ICON_MAX_LEN = "8007-图像不超过"+ COMPANY_ICON_MAX_LEN +"个字符";

    public static final String ERR_NO_ACCT = "9000-兄弟你没有此账号，请勿恶意操作";
    public static final String ERR_NO_PWD = "9001-老弟你没有此密码，请勿恶意操作！";
    public static final String ERR_PRODUCT_ID = "9002-该公司不支持此产品类型，请勿恶意操作";
    public static final String ERR_PWD_TYPE = "9003-该公司不支持此密码类型，请勿恶意添加";
    public static final String ERR_COMPANY_ID_TYPE = "9004-上送的企业ID或类型有误";
    public static final String ERR_AUTH_PRODUCT_NOT_EXIT = "9005-授权产品不存在，请勿恶意操作";
    public static final String ERR_NO_CHANGE = "9006-数据没有变动，请修改后提交";
    public static final String ERR_REQ_DATA = "9009-上送数据有误";


    public static final String ERR_INVITE_CODE = "4001-邀请码不能为空";
    public static final String ERR_USR_TOO_LONG = "4002-用户名长度不超过"+ USR_NAME_MAX_LEN +"个字符";
    public static final String ERR_USR_NOT_NULL = "4003-用户名不能为空";
    public static final String ERR_PWD_TOO_LONG = "4004-密码长度"+ USR_PWD_MIN_LEN +"到"+ USR_PWD_MAX_LEN +"字符";
    public static final String ERR_PWD_NOT_NULL = "4005-密码不能为空";
    public static final String ERR_URE_EXIT = "4006-该用户已注册";
    public static final String ERR_SYS = "9999-系统异常，暂时不能注册";
    public static final String ERR_INVITE_CODE_NOT_EXIT = "4007-邀请码不正确";
    public static final String ERR_INVITE_CODE_LIMIT = "4008-邀请码使用次数过多，联系管理员索要新的验证码";
    public static final String ERR_ACCT_TOO_MUCH = "9010-添加的用户已达上限，添加更多账户请联系管理员";
    public static final String ERR_PED_TOO_MUCH = "9011-添加的密码已达上限，添加更多账户请联系管理员";
    public static final String ERR_SYS_P = "9119-操作过于频繁";


    public static Map<String, List<Integer>> productIdGroupByCompanyTypeMap = new HashMap<>();
    public static Map<String, List<String>> pwdTypeGroupByCompanyTypeMap = new HashMap<>();

    static {
        // 同步前端
        productIdGroupByCompanyTypeMap.put("0", Arrays.asList(1,2,3,4,5,6,7));
        productIdGroupByCompanyTypeMap.put("1", Arrays.asList(1));
        productIdGroupByCompanyTypeMap.put("2", Arrays.asList(8,9,10));
        productIdGroupByCompanyTypeMap.put("3", Arrays.asList(1));
        // 同步前端
        pwdTypeGroupByCompanyTypeMap.put("0", Arrays.asList("1","0","4","5","6"));
        pwdTypeGroupByCompanyTypeMap.put("1", Arrays.asList("0","4","5","6"));
        pwdTypeGroupByCompanyTypeMap.put("2", Arrays.asList("1","0","2","3","5"));
        pwdTypeGroupByCompanyTypeMap.put("3", Arrays.asList("0","4","5","6"));

    }

}
