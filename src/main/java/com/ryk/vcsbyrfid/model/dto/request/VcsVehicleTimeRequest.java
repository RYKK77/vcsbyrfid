package com.ryk.vcsbyrfid.model.dto.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更改正常使用时间
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsVehicleTimeRequest implements Serializable {


    /**
     * 车辆ID
     */
    private Long id;



    /**
     * 正常使用时段
     */
    private String useRange;






    private static final long serialVersionUID = 1L;
}