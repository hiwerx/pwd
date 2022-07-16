package com.lq.pwd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 账号信息
 * </p>
 *
 * @author lq.com
 * @since 2022-06-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("acct")
public class Acct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 厂商id
     */
    @TableField("company_id")
    private Integer companyId;
    /**
     * 账户类型,0网站，1app
     */
    @TableField("acct_type")
    private String acctType;

    /**
     * 账户名
     */
    @TableField("acct_name")
    private String acctName;

    /**
     * 绑定手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 名称
     */
    @TableField("product_id")
    private Integer productId;

    /**
     * 名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 地址
     */
    @TableField("product_url")
    private String productUrl;

    /**
     * 是否有效，1有效，0无效
     */
    @TableField("valid")
    private String valid;

    /**
     * 用户id
     */
    @TableField("usr_id")
    private Integer usrId;


    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("email")
    private String email;


}
