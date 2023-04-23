package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 预警记录
 * @TableName vcs_warning
 */
@TableName(value ="vcs_warning")
@Data
public class VcsWarning implements Serializable {
    /**
     * 预警ID
     */
//    @TableId(value = "id")
    private Long id;

    /**
     * 预警内容
     */
//    @TableField(value = "warning_content")
    private String warningContent;

    /**
     * 预警时间
     */
//    @TableField(value = "created_time")
    private Date createdTime;

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

    /**
     * 设备ID
     */
//    @TableField(value = "device_id")
    private Long deviceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}