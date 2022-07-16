package com.lq.pwd.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 其他登录账户
 * </p>
 *
 * @author lq.com
 * @since 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("other_acct")
public class OtherAcct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("acct_id")
    private Integer acctId;

    @TableField("acct_name")
    private String acctName;

    /**
     * 有效，1有效，0无效
     */
    @TableField("valid")
    private String valid;


}
