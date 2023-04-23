package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
//    @TableId(value = "id")
    private Long id;

    /**
     * 车牌号
     */
//    @TableField(value = "car_number")
    private String carNumber;

    /**
     * 车辆状态
     */
//    @TableField(value = "state")
    private Integer state;

    /**
     * 正常使用时段
     */
//    @TableField(value = "use_range")
    private String useRange;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}