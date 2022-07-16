package com.lq.pwd.vo;

import lombok.Data;

import java.util.List;

@Data
public class AcctPwdVO {

    private Integer acctId;
    private String acctName;
    private String phone;
    private String email;
    private String productName;
    private Integer productId;
    private String productIcon;

    private List<PwdVO> pwdVOList;

}
