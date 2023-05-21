package com.ryk.vcsbyrfid.model.dto.respond;


import lombok.Data;

@Data
public class CarRecordNumber {
    // 当日进校车数
    private String todayInCar;
    // 当日出校车数
    private String todayOutCar;
    // 临时车数
    private String temporalCar;
    // 异常车数
    private String ExceptionCar;
}
