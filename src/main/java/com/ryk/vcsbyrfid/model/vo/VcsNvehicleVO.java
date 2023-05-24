package com.ryk.vcsbyrfid.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆(脱敏）
 * @TableName vcs_nvehicle
 */
@Data
public class VcsNvehicleVO implements Serializable {
    /**
     * 车辆ID
     */
    private Long id;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 车辆状态
     */
    private Integer state;

    /**
     * 正常使用时段
     */
    private String useRange;


    /**
     * 创建时间
     */
    private Date createdTime;


    /**
     * 用户ID
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}