package com.ryk.vcsbyrfid.model.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户创建请求
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsRfidAddRequest implements Serializable {

    /**
     * 有效期
     */
    private Date validDate;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 车辆ID
     */
    private Long nvehicleId;

    private static final long serialVersionUID = 1L;
}