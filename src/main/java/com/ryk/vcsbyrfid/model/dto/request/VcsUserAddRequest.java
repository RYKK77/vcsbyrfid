package com.ryk.vcsbyrfid.model.dto.request;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "sid")
    private String sid;
    @ExcelProperty(value = "phone")
    private String phone;
    /**
     * 用户姓名
     */
    @ExcelProperty(value = "userName")
    private String userName;
    @ExcelProperty(value = "college")
    private String college;
    @ExcelProperty(value = "mail")
    private String mail;
    @ExcelProperty(value = "role")
    private Integer role;

    private static final long serialVersionUID = 1L;
}