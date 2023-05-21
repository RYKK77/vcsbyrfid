package com.ryk.vcsbyrfid.model.dto.respond;

import lombok.Data;

@Data
public class QueryCond {
    private QueryTimeRange queryTimeRange;
    private String carNumber;
    private String accessType;
    private String curState;
    private String warningState;
}
