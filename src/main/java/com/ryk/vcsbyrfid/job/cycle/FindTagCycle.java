package com.ryk.vcsbyrfid.job.cycle;


import com.ryk.vcsbyrfid.model.entity.VcsDevice;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.rfid.UHF.UHFReader;
import com.ryk.vcsbyrfid.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 增量同步帖子到 es
 *
 * @author ryk
 * @from
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class FindTagCycle {
    @Resource
    private UHFReader uhfReader;

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

    /**
     * 0.1s 执行一次
     */
    @Scheduled(fixedRate = 100)
    public void run() throws ParseException {
        if (uhfReader.getStatues() == 0) {
            String[] inventory = uhfReader.Inventory();
            if (inventory != null && inventory.length != 0) {
                System.out.println("find tagName:" + inventory[0]);
                //首先通过车辆ID找到车辆的信息
                VcsNvehicle car = vcsNvehicleService.getById(inventory[0]);
                //创建一个任务对象【检测是否在未授权区域】多线程节省时间
                Runnable target = new Runnable() {
                    @Override
                    public void run() {
                        Long userId = car.getUserId();
                        VcsUser user = vcsUserService.getById(userId);
                        Integer role = user.getRole();//获得用户角色 3/7 有高级进出权限
                        VcsDevice device = vcsDeviceService.getById(1);
                        Integer isSecret = device.getIsSecret();//得到当前区域等级
                        if (isSecret == 1) {
                            if (role != 3 && role != 7) {
                                //TODO 在未授权区域有记录——存入Record数据库&&Warning数据库
                            }
                        }
                    }
                };
                Thread t = new Thread(target);
                t.start();
                Integer state = car.getState();//检查车辆状态
                if (state == 1 || state == 2) {
                    //TODO: 非正常时间预警（当前车辆的状态不应该有记录——存入Record数据库&&Warning数据库
                } else {
                    String useRange = car.getUseRange();
                    String[] validTimes = useRange.split("-");
                    String startTimeStr = validTimes[0];
                    String endTimeStr = validTimes[1];

                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    Date startTime = sdf.parse(startTimeStr);
                    Date endTime = sdf.parse(endTimeStr);
                    long nowTime = now.getTime().getTime();
                    long startTimeInMillis = startTime.getTime();
                    long endTimeInMillis = endTime.getTime();

                    if (nowTime >= startTimeInMillis && nowTime <= endTimeInMillis) {
                        //TODO: 正常时间——存入Record数据库
                        System.out.println("当前时间在有效时间范围内");
                    } else {
                        //TODO: 非正常时间预警（当前车辆的记录不在预设的有效时间范围内——存入Record数据库&&Warning数据库
                        System.out.println("当前时间不在有效时间范围内");
                    }
                }

            }
        }
    }
}
