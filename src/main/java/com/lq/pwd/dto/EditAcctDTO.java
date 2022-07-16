package com.lq.pwd.dto;

import com.lq.pwd.common.Constant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class EditAcctDTO {
    Integer usrId;
    @NotNull(message = Constant.ERR_ACCT_ID)
    private Integer acctId;
    @NotBlank(message = Constant.ERR_ACCT_NAME_NULL)
    @Length(max = Constant.ACCT_MAX_LEN,message = Constant.ERR_ACCT_NAME_TOO_LONG)
    private String acctName;
    @Length(max = Constant.PHONE_MAX_LEN,message = Constant.ERR_PHONE_TOO_LONG)
    private String phone;
    @Email(message = Constant.ERR_EMAIL)
    @Length(max = Constant.EMAIL_MAX_LEN,message = Constant.ERR_EMAIL_TOO_LONG)
    private String email;

}
