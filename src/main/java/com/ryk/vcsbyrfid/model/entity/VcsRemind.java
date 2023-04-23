package com.ryk.vcsbyrfid.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 提醒
 * @TableName vcs_remind
 */
@TableName(value ="vcs_remind")
@Data
public class VcsRemind implements Serializable {
    /**
     * 提醒ID
     */
//    @TableId(value = "id")
    private Long id;

    /**
     * 用户ID
     */
//    @TableField(value = "user_id")
    private Long userId;

    /**
     * 被提醒用户ID
     */
//    @TableField(value = "remind_user_id")
    private Long remindUserId;

    /**
     * 提醒内容
     */
//    @TableField(value = "remind_content")
    private String remindContent;

    /**
     * 提醒时间
     */
//    @TableField(value = "remind_time")
    private Date remindTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}