package com.lq.pwd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 问题与回答,密保答案
 * </p>
 *
 * @author lq.com
 * @since 2022-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("que_ans")
public class QueAns implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 问题
     */
    @TableField("que")
    private String que;

    /**
     * 答案
     */
    @TableField("ans")
    private String ans;

    /**
     * 账户id
     */
    @TableField("acct_id")
    private Integer acctId;

    @TableField("creat_time")
    private LocalDateTime creatTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
