package com.ryk.vcsbyrfid.model.dto.respond;


import lombok.Data;

@Data
public class CarRecord {
    private Long id;
    private String licencePlateNumber;
    private String carType;
    private String accessType;
    private String curState;
    private String userName;
    private String phoneNumber;
    private String recordTime;
    private String recordBayonet;
    private String earlyWarningState;
    private String remarks;
}
