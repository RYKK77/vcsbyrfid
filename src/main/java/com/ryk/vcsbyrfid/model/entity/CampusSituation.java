package com.ryk.vcsbyrfid.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 校园事态画面
 *
 * @author ryk
 * @from  
 */
@Data
public class CampusSituation implements Serializable {
    public CampusBaseInfo campusBaseInfo;
    public CampusSecurity campusSecurity;

}