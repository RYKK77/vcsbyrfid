package com.ryk.vcsbyrfid.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户视图（脱敏）
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsUserVO implements Serializable {

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


    private static final long serialVersionUID = 1L;
}