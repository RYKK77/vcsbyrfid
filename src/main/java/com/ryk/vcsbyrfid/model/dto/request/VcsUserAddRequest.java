package com.ryk.vcsbyrfid.model.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsUserAddRequest implements Serializable {


    /**
     * 用户学号
     */
    private String sid;
    private String phone;
    /**
     * 用户姓名
     */
    private String userName;
    private String college;

    private String mail;


    private Integer role;

    private static final long serialVersionUID = 1L;
}