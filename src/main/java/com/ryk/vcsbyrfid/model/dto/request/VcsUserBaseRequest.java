package com.ryk.vcsbyrfid.model.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author https://github.com/Mr-Jacks520
 * @date 2023/5/25 16:58
 * @description
 */
@Data
public class VcsUserBaseRequest implements Serializable {
    private String userName;
    private String college;
}
