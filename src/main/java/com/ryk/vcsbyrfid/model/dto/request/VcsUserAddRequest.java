package com.ryk.vcsbyrfid.model.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author ryk
 * @from  
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VcsUserAddRequest extends VcsUserBaseRequest implements Serializable {

    /**
     * 用户学号
     */
    private String sid;
    private String phone;

    private String mail;

    private Integer role;

    private static final long serialVersionUID = 1L;
}