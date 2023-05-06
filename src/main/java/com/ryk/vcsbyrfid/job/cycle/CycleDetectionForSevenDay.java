package com.ryk.vcsbyrfid.job.cycle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.entity.VcsRecord;
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
public class CycleDetectionForSevenDay {

    @Resource
    private VcsUserService vcsUserService;

    @Resource
    private VcsRecordService vcsRecordService;

    @Resource
    private VcsNvehicleService vcsNvehicleService;

    @Resource
    private VcsRemindService vcsRemindService;

    @Resource
    private VcsWarningService vcsWarningService;

    @Resource
    private VcsDeviceService vcsDeviceService;

    @Resource
    private VcsRfidService vcsRfidService;

    /**
     * 24H 执行一次
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void run() {
        //长时间未使用提醒
        QueryWrapper<VcsNvehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("carNumber", 0);
        List<VcsNvehicle> list = vcsNvehicleService.list(queryWrapper);//提取所有车辆
        for (VcsNvehicle car : list) {
            Long id = car.getId();
            QueryWrapper<VcsRecord> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("nvehicleId", id);
            //TODO 还要加一个时间限制
            List<VcsRecord> list1 = vcsRecordService.list(queryWrapper1);
            if (list1.size() == 0) {
                // TODO 七天未使用预警
            }

        }


    }
}
