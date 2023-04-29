package com.ryk.vcsbyrfid.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新个人信息请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsUserUpdateMyRequest implements Serializable {


    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String mail;


    private static final long serialVersionUID = 1L;
}