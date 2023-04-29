package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 验证码对照表
 * @TableName vcs_code
 */
@TableName(value ="vcs_code")
@Data
public class VcsCode implements Serializable {
    /**
     * 预警ID
     */
    @TableId
    private Long id;

    /**
     * 验证码
     */
    private Integer code;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}