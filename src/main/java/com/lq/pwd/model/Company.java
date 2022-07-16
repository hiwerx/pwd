package com.lq.pwd.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 厂商，公司信息
 * </p>
 *
 * @author lq.com
 * @since 2022-06-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 厂商名称
     */
    @TableField("name")
    private String name;

    /**
     * 厂商地址
     */
    @TableField("url")
    private String url;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 厂商类别，0互联网，1电信通讯，2银行，9其他
     */
    @TableField("type")
    private String type;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    @TableField("usr_id")
    private Integer usrId;


}
