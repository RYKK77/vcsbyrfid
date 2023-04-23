package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 日志存储
 * @TableName vcs_log
 */
@TableName(value ="vcs_log")
@Data
public class VcsLog implements Serializable {
    /**
     * 日志ID
     */
//    @TableId(value = "id")
    private Long id;

    /**
     * 存储地址
     */
//    @TableField(value = "storage_path")
    private String storagePath;

    /**
     * 记录时间范围
     */
//    @TableField(value = "record_range")
    private String recordRange;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}