package com.ryk.vcsbyrfid.model.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsUserUpdateRequest implements Serializable {
    /**
     * id
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
     * 学院
     */
    private String college;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String mail;


    /**
     * 用户角色
     */
    private Integer role;


    private static final long serialVersionUID = 1L;
}