package com.ryk.vcsbyrfid.model.dto.respond;

import lombok.Data;

@Data
public class DangerBoardcast {
    // 警情等级
    public Integer level;
    public String recordTime;
    public String recordBayonet;
    public String recordCause;
    public String carNumber;
    // 处理状态
    public Integer handleStatus;
}
