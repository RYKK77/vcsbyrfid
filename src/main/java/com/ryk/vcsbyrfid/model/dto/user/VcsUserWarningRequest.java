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
public class VcsUserWarningRequest implements Serializable {


    /**
     * id
     */
    private String id;

    /**
     * 用户邮箱
     */
    private String warningMsg;


    private static final long serialVersionUID = 1L;
}