package com.lq.pwd.dto;

import com.lq.pwd.common.Constant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CompanyDTO {

    @NotBlank(message = Constant.ERR_COMPANY_NAME_NULL)
    @Length(max = Constant.COMPANY_NAME_MAX_LEN, message = Constant.ERR_COMPANY_NAME_MAX_LEN)
    private String producerName;
    @Length(max = Constant.COMPANY_URL_MAX_LEN, message = Constant.ERR_COMPANY_URL_MAX_LEN)
    private String url;
    @Length(max = Constant.PHONE_MAX_LEN, message = Constant.ERR_COMPANY_PHONE_MAX_LEN)
    private String phone;
    @NotBlank(message = Constant.ERR_COMPANY_TYPE_NULL)
    private String type;
    @Length(max = Constant.COMPANY_ICON_MAX_LEN, message = Constant.ERR_COMPANY_ICON_MAX_LEN)
    private String icon;
    private Integer usrId;
}
