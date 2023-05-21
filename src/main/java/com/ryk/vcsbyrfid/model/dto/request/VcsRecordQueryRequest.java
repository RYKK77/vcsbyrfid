package com.ryk.vcsbyrfid.model.dto.request;

import com.ryk.vcsbyrfid.common.PageRequest;
import com.ryk.vcsbyrfid.model.dto.respond.QueryCond;
import com.ryk.vcsbyrfid.model.dto.respond.QueryTimeRange;
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
     * 用户ID
     */
    private Long userId;

    private QueryCond queryCond;


    /**
     * 记录类型
     */
    private Integer type;



    private static final long serialVersionUID = 1L;
}