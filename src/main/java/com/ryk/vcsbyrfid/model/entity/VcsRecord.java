package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录
 * @TableName vcs_record
 */
@TableName(value ="vcs_record")
@Data
public class VcsRecord implements Serializable {
    /**
     * 记录ID
     */
//    @TableId(value = "id")
    private Long id;

    /**
     * 记录类型
     */
//    @TableField(value = "type")
    private Integer type;

    /**
     * 创建时间
     */
//    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 设备ID
     */
//    @TableField(value = "device_id")
    private Long deviceId;

    /**
     * 车辆ID
     */
//    @TableField(value = "nvehicle_id")
    private Long nvehicleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}