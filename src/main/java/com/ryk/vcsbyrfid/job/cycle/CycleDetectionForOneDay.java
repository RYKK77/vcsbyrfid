package com.ryk.vcsbyrfid.job.cycle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.entity.VcsRemind;
import com.ryk.vcsbyrfid.model.entity.VcsRfid;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.rfid.UHF.UHFReader;
import com.ryk.vcsbyrfid.service.*;
import com.ryk.vcsbyrfid.utils.SendMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.ryk.vcsbyrfid.constant.CommonConstant.SEND_REMIND_LACK_TEMPLATE_ID;

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
    private VcsRemindService vcsRemindService;


    @Resource
    private VcsRfidService vcsRfidService;

    @Resource
    private VcsNvehicleService vcsNvehicleService;

    @Resource
    private VcsUserService vcsUserService;

    @Resource
    private SendMsg sendMsg;

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
                Long userId = rfid.getUserId();
                VcsRemind vcsRemind = new VcsRemind();
                vcsRemind.setRemindContent("用户：" + userId + "已过期，需要续费");
                QueryWrapper<VcsUser> userQuery = new QueryWrapper<>();
                userQuery.eq("userId", userQuery);
                List<VcsUser> users = vcsUserService.list(userQuery);
                String phone = users.get(0).getPhone();
                VcsNvehicle car = vcsNvehicleService.getById(rfid.getNvehicleId());

                sendMsg.sendMegToUser(phone, SEND_REMIND_LACK_TEMPLATE_ID, null, null, null, car.getCarNumber());
                vcsRemind.setUserId(userId);
                vcsRemind.setRemindUserId(userId);
                vcsRemindService.save(vcsRemind);
                log.info("用户：" + userId + "已过期，需要续费");
            }
        }


    }
}
