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
public class SmsRecord implements Serializable {
    public String msg;
    public String smsid;
    public Integer code;
    public String balance;
}