package com.lq.pwd.dto;

import com.lq.pwd.common.Constant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class AddUsrDTO {
    @NotBlank(message = Constant.ERR_USR_NOT_NULL)
    @Length(max = Constant.USR_NAME_MAX_LEN, message = Constant.ERR_USR_TOO_LONG)
    private String name;
    @NotBlank(message = Constant.ERR_PWD_NOT_NULL)
    @Length(min = Constant.USR_PWD_MIN_LEN,max = Constant.USR_PWD_MAX_LEN, message = Constant.ERR_PWD_TOO_LONG)
    private String pwd;
    @NotBlank(message = Constant.ERR_INVITE_CODE)
    private String inviteCode;
}
