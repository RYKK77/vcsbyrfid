package com.ryk.vcsbyrfid.job.cycle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryk.vcsbyrfid.model.entity.VcsRfid;
import com.ryk.vcsbyrfid.rfid.UHF.UHFReader;
import com.ryk.vcsbyrfid.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 增量同步帖子到 es
 *
 * @author ryk
 * @from
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class CycleDetectionForOneDay {

    @Resource
    private VcsUserService vcsUserService;


    @Resource
    private VcsNvehicleService vcsNvehicleService;

    @Resource
    private VcsRemindService vcsRemindService;


    @Resource
    private VcsRfidService vcsRfidService;

    /**
     * 24H 执行一次
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void run() {
        //续费提醒
        QueryWrapper<VcsRfid> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("userId", 0);
        List<VcsRfid> rfids = vcsRfidService.list(queryWrapper);//提取所有用户
        for (VcsRfid rfid : rfids) {
            Date validDate = rfid.getValidDate();
            Date now = new Date();
            long givenTimeInMillis = validDate.getTime();
            long nowTimeInMillis = now.getTime();
            if (nowTimeInMillis < givenTimeInMillis) {
                Long userId = rfid.getUserId();
                System.out.println("用户：" + userId + "在有效期内，不需要续费");
            } else {
                // TODO ——存入Remind数据库
                Long userId = rfid.getUserId();
                System.out.println("用户：" + userId + "已过期，需要续费");
            }
        }


    }
}
