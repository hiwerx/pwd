package com.lq.pwd.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lq.pwd.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.beans.Transient;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcctPwdDTO {
    // 厂商id
    @NotBlank(message = Constant.ERR_COMPANY_ID_TYPE)
    @Pattern(regexp = "\\d{1,9999}-\\d{1,9999}",message = Constant.ERR_COMPANY_ID_TYPE)
    private String companyIdType;

    @JSONField(serialize = false)
    private Integer companyId;
    @JSONField(serialize = false)
    private String companyType;
    @JSONField(serialize = false)
    private Integer acctId;

    /**
     * 密码用于的产品类型，全部，部分，或银行卡
     */
    @NotNull(message = Constant.ERR_PRODUCT_ID_NULL)
    @Digits(integer = 10,fraction = 0,message = Constant.ERR_REQ_DATA)
    private Integer productId;

    /**
     * 账户名
     */
    @NotBlank(message = Constant.ERR_ACCT_NAME_NULL)
    @Length(max = Constant.ACCT_MAX_LEN,message = Constant.ERR_ACCT_NAME_TOO_LONG)
    private String acctName;

    /**
     * 密码类型，0登录密码，1支付密码, 2银行卡交易密码，3ukey登录密码，4授权登录，5手机验证码登录
     */
    @NotBlank(message = Constant.ERR_PWD_TYPE_NULL)
    @Pattern(regexp = "\\d",message = Constant.ERR_REQ_DATA)
    private String pwdType;

    /**
     * 授权登录时授权的产品
     */
    private Integer authProductId;

    @NotBlank(message = Constant.ERR_PWD_NULL)
   // @Length(max = Constant.PWD_MAX_LEN,message = Constant.ERR_ACCT_PWD_TOO_LONG)
    private String pwd;

    /**
     * 绑定手机号
     */
    @Length(max = Constant.PHONE_MAX_LEN,message = Constant.ERR_PHONE_TOO_LONG)
    private String phone;

    /**
     * 用户id
     */
    @JSONField(serialize = false)
    private Integer usrId;

    private Integer pwdId;

    @Email(message = Constant.ERR_EMAIL)
    @Length(max = Constant.EMAIL_MAX_LEN,message = Constant.ERR_EMAIL_TOO_LONG)
    private String email;

    public static void main(String[] args) {
        System.out.println("1-2".matches("\\d{1,999}\\-\\d{1,999}"));
        System.out.println(JSON.toJSONString(new AcctPwdDTO(), SerializerFeature.WriteMapNullValue));
    }
}
