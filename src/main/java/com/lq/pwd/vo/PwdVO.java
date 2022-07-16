package com.lq.pwd.vo;

import lombok.Data;

@Data
public class PwdVO {
    
    private Integer pwdId;
    private String pwd;
    private String pwdType;
    private Integer authProductId;
    private String authProductName;
    
}
