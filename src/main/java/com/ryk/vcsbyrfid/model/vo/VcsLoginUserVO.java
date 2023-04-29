package com.ryk.vcsbyrfid.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 已登录用户视图（脱敏）
 *
 * @author ryk
 * @from  
 **/
@Data
public class VcsLoginUserVO implements Serializable {

    /**
     * 用户ID
     */
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
     * 用户手机号
     */
    private String phone;

    /**
     * 用户角色
     */
    private Integer role;


    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}