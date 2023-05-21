package com.ryk.vcsbyrfid.model.dto.respond;

import com.ryk.vcsbyrfid.model.entity.VisitorManage;
import lombok.Data;

@Data
public class CampusBaseInfo {
    // 车辆总数
    public String campusPersons;
    public String campusCarNums;
    public CarSum carSum[];

    // 车辆进出统计

    public String todayInCars;
    public String todayOutCars;

    public AccessStatistics accessStatistics[];

    // 访客管理
    public VisitorManage visitorManage[];
}
