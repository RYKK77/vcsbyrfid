package com.ryk.vcsbyrfid.model.dto.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsVehicleStatusRequest implements Serializable {



    /**
     * 车辆ID
     */
    private Long id;



    /**
     * 车辆状态
     */
    private Integer state;

    private static final long serialVersionUID = 1L;
}