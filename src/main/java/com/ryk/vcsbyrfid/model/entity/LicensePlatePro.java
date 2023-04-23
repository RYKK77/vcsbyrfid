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
public class LicensePlatePro implements Serializable {
    private String number;
    private String color;
}