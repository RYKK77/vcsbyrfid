package com.ryk.vcsbyrfid.model.dto.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 车辆信息更新请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsVehicleUpdateRequest implements Serializable {

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
     * 用户ID
     */
    private Long userId;


    /**
     * 车辆类型
     */
    private String mold;

    private static final long serialVersionUID = 1L;
}