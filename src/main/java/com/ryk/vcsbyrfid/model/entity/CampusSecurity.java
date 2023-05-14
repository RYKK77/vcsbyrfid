package com.ryk.vcsbyrfid.model.entity;

import lombok.Data;

@Data
public class CampusSecurity {
    // 安全设施监控情况
    public String monitorCamera;
    public String RFIDDevice;
    public MonitorInfo monitorInfo[];

    // 当日警情播报
    public DangerBoardcast dangerBoardcast[];

    // 警情类型统计
    public DangerStatistics dangerStatistics[];
}
