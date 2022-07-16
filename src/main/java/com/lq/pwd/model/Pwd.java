package com.lq.pwd.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 密码表
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
@TableName("pwd")
public class Pwd implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("pwd")
    private String pwd;

    @TableField("auth_product_id")
    private Integer authProductId;

    /**
     * 是否有效，1有效，0无效
     */
    @TableField("valid")
    private String valid;

    /**
     * 密码类型，0登录密码，1支付密码, 2银行卡交易密码，2ukey登录密码
     */
    @TableField("pwd_type")
    private String pwdType;

    /**
     * 账户id
     */
    @TableField("acct_id")
    private Integer acctId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
