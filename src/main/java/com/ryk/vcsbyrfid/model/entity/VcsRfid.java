package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.*;

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
//    @TableId(value = "id")
    private Long id;

    /**
     * 校验码
     */
//    @TableField(value = "check_code")
    private String checkCode;

    /**
     * 有效期
     */
//    @TableField(value = "valid_date")
    private Date validDate;

    /**
     * 是否有效
     */
    @TableLogic
    @TableField(value = "is_valid")
    private Integer isValid;

    /**
     * 创建时间
     */
//    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
//    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 用户ID
     */
//    @TableField(value = "user_id")
    private Long userId;

    /**
     * 车辆ID
     */
//    @TableField(value = "nvehicle_id")
    private Long nvehicleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}