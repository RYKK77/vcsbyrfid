package com.ryk.vcsbyrfid.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsUserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String sid;

    private String password;
}
