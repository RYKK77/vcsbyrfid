package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志关联
 * @TableName user_log_operation
 */
@TableName(value ="user_log_operation")
@Data
public class UserLogOperation implements Serializable {
    /**
     * 日志ID
     */
    @TableId(value = "log_id")
    private Long logId;

    /**
     * 用户ID
     */
//    @TableId(value = "user_id")
    private Long userId;

    /**
     * 操作时间
     */
//    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}