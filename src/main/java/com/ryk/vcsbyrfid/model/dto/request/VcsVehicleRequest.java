package com.ryk.vcsbyrfid.model.dto.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆信息创建请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsVehicleRequest implements Serializable {



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