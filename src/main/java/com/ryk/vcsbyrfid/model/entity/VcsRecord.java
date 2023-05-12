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
    @TableId
    private Long id;

    /**
     * 记录类型
     * 0-正常
     * 1-长时停车预警（7d）
     * 2-未授权区域闯入预警
     * 3-非正常时间预警
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 车辆ID
     */
    private Long nvehicleId;

    /**
     * 用户ID
     */
    private Long userId;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}