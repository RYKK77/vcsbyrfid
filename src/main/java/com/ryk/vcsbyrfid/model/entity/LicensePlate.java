package com.ryk.vcsbyrfid.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 车牌
 *
 * @author ryk
 * @from  
 */
//@TableName(value = "request")
@Data
public class LicensePlate implements Serializable {
    public int ret;
    public String msg;
    public String log_id;
    public LicensePlatePro data;
}