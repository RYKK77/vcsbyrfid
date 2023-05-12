package com.ryk.vcsbyrfid.job.cycle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryk.vcsbyrfid.model.entity.*;
import com.ryk.vcsbyrfid.rfid.UHF.UHFReader;
import com.ryk.vcsbyrfid.service.*;
import com.ryk.vcsbyrfid.utils.SendMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.ryk.vcsbyrfid.constant.CommonConstant.SEND_WARNING_NO_RECORDS_TEMPLATE_ID;

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
    private SendMsg sendMsg;

    @Resource
    private VcsRfidService vcsRfidService;

    /**
     * 24H 执行一次
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void run() {
        //长时间未使用提醒
        QueryWrapper<VcsNvehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("state", 0);
        List<VcsNvehicle> list = vcsNvehicleService.list(queryWrapper);//提取所有正常使用的车辆

        for (VcsNvehicle car : list) {
            Long id = car.getId();
            //加一个时间限制,排除7天内已有长时预警记录的车辆
            QueryWrapper<VcsWarning> warningQueryWrapper = new QueryWrapper<>();
            warningQueryWrapper.eq("nvehicleId", id);
            warningQueryWrapper.eq("warningType", "1");
            warningQueryWrapper.ge("createdTime", LocalDateTime.now().minusDays(7));
            List<VcsWarning> vcsWarnings = vcsWarningService.list(warningQueryWrapper);
            if (vcsWarnings.size() > 0) {
                continue;//如果已经有记录，就忽略
            }
            QueryWrapper<VcsRecord> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("nvehicleId", id);
            List<VcsRecord> list1 = vcsRecordService.list(queryWrapper1);
            if (list1.size() == 0) {
                // 七天未使用预警
                VcsWarning vcsWarning = new VcsWarning();
                vcsWarning.setDeviceId(1L);
                vcsWarning.setWarningType("1");
                vcsWarning.setWarningContent("您好，您的车辆已静止停车超过7天，如您已离开学校，请及时更改自己的车辆状态");
                VcsUser user = vcsUserService.getById(car.getUserId());
                String phone = user.getPhone();
                sendMsg.sendMegToUser(phone, SEND_WARNING_NO_RECORDS_TEMPLATE_ID, null,
                        "7 * 24", null, car.getCarNumber());
                vcsWarning.setUserId(car.getUserId());
                vcsWarning.setNvehicleId(id);
                vcsWarningService.save(vcsWarning);
            }
        }


    }
}
