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
    @TableId
    private Long id;

    /**
     * 预警内容
     */
    private String warningContent;

    /**
     * 预警类型
     * 1-长时停车预警（7d）
     * 2-未授权区域闯入预警
     * 3-非正常时间预警
     */
    private String warningType;

    /**
     * 预警时间
     */
    private Date createdTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 车辆ID
     */
    private Long nvehicleId;

    /**
     * 设备ID
     */
    private Long deviceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}