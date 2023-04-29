package com.ryk.vcsbyrfid.model.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ryk.vcsbyrfid.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 * @author ryk
 * @from  
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VcsUserQueryRequest extends PageRequest implements Serializable {
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