package com.ryk.vcsbyrfid.model.dto.respond;

import com.ryk.vcsbyrfid.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询返回
 *
 * @author ryk
 * @from  
 */
@Data
public class VcsRecordQueryRespond implements Serializable {

    public Long total;

    public Long pages;
    public Long current;

    public Records records;


    private static final long serialVersionUID = 1L;
}