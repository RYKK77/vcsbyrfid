package com.ryk.vcsbyrfid.model.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsUserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String sid;

    /**
     * 用户姓名
     */
    private String userName;
    private String college;

    private String phone;

    private String mail;

    private String password;

    private String checkPassword;

    private Integer role;
}
