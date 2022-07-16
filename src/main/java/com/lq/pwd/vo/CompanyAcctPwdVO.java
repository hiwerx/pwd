package com.lq.pwd.vo;

import lombok.Data;

import java.util.List;

@Data
public class CompanyAcctPwdVO {

    private Integer companyId;
    private String companyName;
    private String url;
    private String companyPhone;
    private String companyType;
    private String companyIcon;

    private List<AcctPwdVO> acctVOList;
}
