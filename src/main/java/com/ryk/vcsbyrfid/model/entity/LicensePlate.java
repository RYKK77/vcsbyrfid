package com.ryk.vcsbyrfid.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 车牌
 *
 * @author ryk
 * @from  
 */
//@TableName(value = "user")
@Data
public class LicensePlate implements Serializable {
    private int ret;
    private String msg;
    private String log_id;
    private LicensePlatePro data;
}