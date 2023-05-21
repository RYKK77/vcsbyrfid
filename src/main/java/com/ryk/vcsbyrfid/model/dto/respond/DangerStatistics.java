package com.ryk.vcsbyrfid.model.dto.respond;

import lombok.Data;

@Data
public class DangerStatistics {

    public String timeoutRemind;
    public String outUseRangeWarning;
    // 续费提醒
    public String renewRemind;
}
