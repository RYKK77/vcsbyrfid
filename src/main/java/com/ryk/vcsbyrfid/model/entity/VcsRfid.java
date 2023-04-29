package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * RFID信息
 * @TableName vcs_rfid
 */
@TableName(value ="vcs_rfid")
@Data
public class VcsRfid implements Serializable {
    /**
     * 信息ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 校验码
     */
    private String checkCode;

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 是否有效
     */
    private Integer isValid;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 车辆ID
     */
    private Long nvehicleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}