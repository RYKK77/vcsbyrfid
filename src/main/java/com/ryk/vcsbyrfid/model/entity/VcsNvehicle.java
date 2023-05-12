package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆
 * @TableName vcs_nvehicle
 */
@TableName(value ="vcs_nvehicle")
@Data
public class VcsNvehicle implements Serializable {
    /**
     * 车辆ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车辆状态
     * 0-正常使用
     * 1-放假长时间停留
     * 2-闲置在校
     */
    private Integer state;

    /**
     * 正常使用时段
     */
    private String useRange;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;

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
     * 车辆类型
     */
    private String mold;

    private Long lastPlaceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}