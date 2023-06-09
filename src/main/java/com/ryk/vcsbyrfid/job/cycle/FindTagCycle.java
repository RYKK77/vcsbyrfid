package com.ryk.vcsbyrfid.job.cycle;


import com.ryk.vcsbyrfid.model.entity.*;
import com.ryk.vcsbyrfid.utils.rfid.UHF.UHFReader;
import com.ryk.vcsbyrfid.service.*;
import com.ryk.vcsbyrfid.utils.SendMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.ryk.vcsbyrfid.constant.CommonConstant.SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID;
import static com.ryk.vcsbyrfid.constant.CommonConstant.SEND_WARNING_SENSITIVE_DETECT_TEMPLATE_ID;

/**
 * 增量同步帖子到 es
 *
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
    private VcsWarningService vcsWarningService;

    @Resource
    private VcsDeviceService vcsDeviceService;

    @Resource
    private SendMsg sendMsg;

    /**
     * 0.1s 执行一次
     */
    @Scheduled(fixedRate = 1000)
    public void run() throws ParseException {
        if (uhfReader.getStatues() == 0) {
            String[] inventory = uhfReader.Inventory();
            if (inventory != null && inventory.length != 0) {
                System.out.println("find tagName:" + inventory[0]);
                //首先通过车辆ID找到车辆的信息
                VcsNvehicle car = vcsNvehicleService.getById(inventory[0]);
                //所有记录均存入Record数据库
                VcsRecord vcsRecord = new VcsRecord();
                vcsRecord.setType(0);
                vcsRecord.setUserId(car.getUserId());
                vcsRecord.setNvehicleId(car.getId());
                vcsRecord.setDeviceId(1L);
                vcsRecordService.save(vcsRecord);

                //创建一个任务对象【检测是否在未授权区域】多线程节省时间
                Runnable target = () -> {
                    Long userId = car.getUserId();
                    VcsUser user = vcsUserService.getById(userId);
                    Integer role = user.getRole();//获得用户角色 3/7 有高级进出权限
                    VcsDevice device = vcsDeviceService.getById(1);//更改当前设备信息（测试需要！！！
                    Integer isSecret = device.getIsSecret();//得到当前区域等级
                    if (isSecret == 1) {
                        if (role != 3 && role != 7) {
                            // 在未授权区域有记录——存入Warning数据库
                            sendMsg.sendMegToUser(user.getPhone(), SEND_WARNING_SENSITIVE_DETECT_TEMPLATE_ID,
                                    null, null, null, car.getCarNumber());
                            VcsWarning vcsWarning = new VcsWarning();
                            vcsWarning.setDeviceId(1L);
                            vcsWarning.setWarningType("2");
                            vcsWarning.setWarningContent("进入未授权区域");
                            log.info("您好，您的车辆" + car.getCarNumber() + "已进入未授权区域，为了避免不必要的麻烦，请立即离开！");
                            vcsWarning.setUserId(car.getUserId());
                            vcsWarning.setNvehicleId(car.getId());
                            vcsWarningService.save(vcsWarning);
                            vcsRecord.setType(1);
                            vcsRecordService.updateById(vcsRecord);
//
//                                VcsRecord vcsRecord = new VcsRecord();
//                                vcsRecord.setType(2);
//                                vcsRecord.setUserId(car.getUserId());
//                                vcsRecord.setNvehicleId(car.getId());
//                                vcsRecord.setDeviceId(1L);
//                                vcsRecordService.save(vcsRecord);
                        }
                    }
                };
                Thread t = new Thread(target);
                t.start();
                Integer state = car.getState();//检查车辆状态
                if (state == 1 || state == 2) {
                    // 非正常时间预警（当前车辆的状态不应该有记录——存入Record数据库&&Warning数据库

                    Long userId = car.getUserId();
                    VcsUser user = vcsUserService.getById(userId);
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
                    sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
                    sendMsg.sendMegToUser(user.getPhone(), SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID, null,
                            sdf.format(date), "西门", car.getCarNumber());
                    // 与预设记录匹配，看是否移动
                    if (car.getLastPlaceId() != 0) {
                        VcsWarning vcsWarning = new VcsWarning();
                        vcsWarning.setDeviceId(1L);
                        vcsWarning.setWarningType("3");
                        vcsWarning.setWarningContent("车辆状态与实际不符");
                        log.info("您好，系统检测到您的车辆" + car.getCarNumber() + "在校内有活动记录，与您预设的车辆状态不符，请及时关注！");
                        vcsWarning.setUserId(car.getUserId());
                        vcsWarning.setNvehicleId(car.getId());
                        vcsWarningService.save(vcsWarning);
                        vcsRecord.setType(1);
                        vcsRecordService.updateById(vcsRecord);
                    }
                } else {
                    String useRange = car.getUseRange();
                    String[] validTimes = useRange.split("-");
                    String startTimeStr = validTimes[0];
                    String endTimeStr = validTimes[1];

                    Calendar now = Calendar.getInstance();
                    now.set(1970, Calendar.JANUARY, 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    Date startTime = sdf.parse(startTimeStr);
                    Date endTime = sdf.parse(endTimeStr);
                    long nowTime = now.getTime().getTime();
                    long startTimeInMillis = startTime.getTime();
                    long endTimeInMillis = endTime.getTime();

                    if (nowTime >= startTimeInMillis && nowTime <= endTimeInMillis) {
                        log.info("当前车辆：" + car.getCarNumber() + "时间在有效时间范围内");
                    } else {
                        //非正常时间预警-当前车辆的记录不在预设的有效时间范围内——存入&Warning数据库

                        Long userId = car.getUserId();
                        VcsUser user = vcsUserService.getById(userId);
                        Date date = new Date();
//                        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
                        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
                        sendMsg.sendMegToUser(user.getPhone(), SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID, null,
                                sdf.format(date), "西门", car.getCarNumber());
                        VcsWarning vcsWarning = new VcsWarning();
                        vcsWarning.setDeviceId(1L);
                        vcsWarning.setWarningType("3");
                        vcsWarning.setWarningContent("车辆使用时间不符");
                        log.info("您好，系统检测到车辆" + car.getCarNumber() + "在校内有活动记录，与您预设的车辆正常使用时间不符，请及时关注！");
                        vcsWarning.setUserId(car.getUserId());
                        vcsWarning.setNvehicleId(car.getId());
                        vcsWarningService.save(vcsWarning);
                        vcsRecord.setType(1);
                        vcsRecordService.updateById(vcsRecord);
                    }
                }

            }
        }
    }
}
