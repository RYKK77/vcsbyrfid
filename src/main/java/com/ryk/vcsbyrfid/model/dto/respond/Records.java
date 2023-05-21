package com.ryk.vcsbyrfid.model.dto.respond;

import lombok.Data;

@Data
public class Records {
    private CarRecord[] carRecord;
    private CarRecordNumber carNumber;
}
