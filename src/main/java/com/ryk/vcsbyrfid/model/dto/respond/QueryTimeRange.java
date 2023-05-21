package com.ryk.vcsbyrfid.model.dto.respond;

import lombok.Data;

import java.util.Date;

@Data
public class QueryTimeRange {
    private Date start;
    private Date end;
}
