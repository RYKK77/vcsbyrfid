package com.ryk.vcsbyrfid.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author https://github.com/Mr-Jacks520
 * @date 2023/5/24 23:49
 * @description 车辆管理通用返回包装类
 */
@Data
@Builder
public class VcsCarVO implements Serializable {
    /**
     * 车辆ID
     */
    private Long id;

    /**
     * 车牌号
     */
    private String carNumber;

    /**
     * 正常使用时段
     */
    private String useRange;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 车辆类型
     */
    private String mold;

    /**
     * RFID
     */
    private String rfid;

    /**
     * 有效期
     */
    private String validTime;

    private static final long serialVersionUID = 1L;
}
