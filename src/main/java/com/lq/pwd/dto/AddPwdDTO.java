package com.lq.pwd.dto;

import com.lq.pwd.common.Constant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddPwdDTO {
    private Integer usrId;
    @NotBlank(message = Constant.ERR_COMPANY_TYPE_NULL)
    private String companyType;
    @NotNull(message = Constant.ERR_ACCT_ID_NULL)
    private Integer acctId;
    @NotBlank(message = Constant.ERR_ACCT_NAME_NULL)
    @Length(max = Constant.ACCT_MAX_LEN,message = Constant.ERR_ACCT_NAME_TOO_LONG)
    private String acctName;
    @NotBlank(message = Constant.ERR_PWD_TYPE_NULL)
    private String pwdType;
    private Integer authProductId;
    @NotBlank(message = Constant.ERR_PWD_NULL)
//    @Length(max = Constant.PWD_MAX_LEN,message = Constant.ERR_ACCT_PWD_TOO_LONG)
    private String pwd;
}
