package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备
 * @TableName vcs_device
 */
@TableName(value ="vcs_device")
@Data
public class VcsDevice implements Serializable {
    /**
     * 设备ID
     */
//    @TableId(value = "id")
    private Long id;

    /**
     * 设备类型
     */
//    @TableField(value = "dName")
    private String dname;

    /**
     * 经度
     */
//    @TableField(value = "longitude")
    private String longitude;

    /**
     * 纬度
     */
//    @TableField(value = "latitude")
    private String latitude;

    /**
     * 是否有效
     */
//    @TableField(value = "is_valid")
    private Integer isValid;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}