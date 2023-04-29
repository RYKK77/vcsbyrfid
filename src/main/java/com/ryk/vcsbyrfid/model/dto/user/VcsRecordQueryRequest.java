package com.ryk.vcsbyrfid.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ryk.vcsbyrfid.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 * @author ryk
 * @from  
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VcsRecordQueryRequest extends PageRequest implements Serializable {

    /**
     * 记录类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 车辆ID
     */
    private Long nvehicleId;

    /**
     * 用户ID
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}