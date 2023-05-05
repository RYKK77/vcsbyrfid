package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName vcs_user
 */
@TableName(value ="vcs_user")
@Data
public class VcsUser implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户学号
     */
    private String sid;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户角色
     * 0-系统管理员
     * 1-监控中心人员
     * 2-普通师生
     * 3-具有高级进出权限的师生
     * 4-校内普通职工
     * 5-校内商家
     * 6-普通访客
     * 7-具有高级进出权限的访客
     */
    private Integer role;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}