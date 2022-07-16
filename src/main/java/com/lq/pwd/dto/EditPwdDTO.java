package com.lq.pwd.dto;

import com.lq.pwd.common.Constant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class EditPwdDTO {
    Integer usrId;
    private Integer pwdId;
    @NotBlank(message = Constant.ERR_PWD_TYPE_NULL)
    private String pwdType;
    @NotBlank(message = Constant.ERR_PWD_NULL)
//    @Length(max = Constant.PWD_MAX_LEN,message = Constant.ERR_ACCT_PWD_TOO_LONG)
    private String pwd;
    private Integer authProductId;

}
