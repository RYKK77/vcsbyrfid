package com.ryk.vcsbyrfid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryk.vcsbyrfid.common.BaseResponse;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.common.ResultUtils;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.model.dto.respond.*;
import com.ryk.vcsbyrfid.model.entity.*;
import com.ryk.vcsbyrfid.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * 用户接口
 *
 * @author ryk
 */
@RestController
@RequestMapping("/campus")
@Slf4j
public class CampusSituationController {

    @Resource
    private VcsUserService vcsUserService;
    @Resource
    private VcsNvehicleService vcsNvehicleService;

    @Resource
    private VcsRecordService vcsRecordService;

    @Resource
    private VcsWarningService vcsWarningService;

    @Resource
    private VcsDeviceService vcsDeviceService;


    /**
     * 获取校园基本事态（仅管理员）
     *
     */
    @GetMapping("/Situation")
    public BaseResponse<CampusSituation> getCampusSituation(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!vcsUserService.isAdmin(request)) {
            //管理员鉴权
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);
        }

        CampusSituation campusSituation = new CampusSituation();

        CampusBaseInfo campusBaseInfo = new CampusBaseInfo();
        // 车辆总数
        campusBaseInfo.setCampusPersons(String.valueOf(vcsUserService.list().size()));
        campusBaseInfo.setCampusCarNums(String.valueOf(vcsNvehicleService.list().size()));
        CarSum[] carSums = new CarSum[5];
        for (int i = 0; i <5; i++) {
            carSums[i] = new CarSum();
            Random random = new Random();
            int randomNumber = random.nextInt(201);
            carSums[i].setCarNums(String.valueOf(randomNumber));
        }
        carSums[0].setCollege("计算机学院");
        carSums[1].setCollege("人文学院");
        carSums[2].setCollege("数学学院");
        carSums[3].setCollege("物理学院");
        carSums[4].setCollege("信息学院");
        campusBaseInfo.setCarSum(carSums);
        // 车辆进出统计
        campusBaseInfo.setTodayInCars(String.valueOf(vcsRecordService.list().size()));
        campusBaseInfo.setTodayOutCars(String.valueOf(vcsRecordService.list().size()-35));
        AccessStatistics[] accessStatistics = new AccessStatistics[4];
        for (int i = 0; i < 4;i++) {
            accessStatistics[i] = new AccessStatistics();
            Random random = new Random();
            int randomNumber = random.nextInt(1001);
            accessStatistics[i].setStatistics(String.valueOf(randomNumber));
        }
        accessStatistics[0].setDoor("西门");
        accessStatistics[1].setDoor("南门");
        accessStatistics[2].setDoor("北门");
        accessStatistics[3].setDoor("东门");

        // 访客管理
        VisitorManage[] visitorManages = new VisitorManage[5];
        for (int i = 0; i< 5; i++) {
            visitorManages[i] = new VisitorManage();
            Random random = new Random();
            int randomNumber = random.nextInt(301);
            visitorManages[i].setTotal(String.valueOf(randomNumber));
            randomNumber = random.nextInt(randomNumber);
            visitorManages[i].setInCampusNum(String.valueOf(randomNumber));

        }
        visitorManages[0].setCollege("计算机学院");
        visitorManages[1].setCollege("人文学院");
        visitorManages[2].setCollege("数学学院");
        visitorManages[3].setCollege("物理学院");
        visitorManages[4].setCollege("信息学院");
        campusBaseInfo.setAccessStatistics(accessStatistics);
        campusBaseInfo.setVisitorManage(visitorManages);

        campusSituation.setCampusBaseInfo(campusBaseInfo);



        CampusSecurity campusSecurity = new CampusSecurity();
        // 安全设施监控情况
        int[] temp = new int[5];
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(301);
            temp[i] = randomNumber;
        }
        campusSecurity.setMonitorCamera(String.valueOf(temp[1]));
        MonitorInfo[] monitorInfos = new MonitorInfo[5];
        for (int i = 0; i <5; i++) {
            monitorInfos[i] = new MonitorInfo();
            monitorInfos[i].setNums(String.valueOf(temp[i]));
        }
        campusSecurity.setRFIDDevice("355");
        monitorInfos[0].setFacility("关键卡口");
        monitorInfos[1].setFacility("监控摄像头");
        monitorInfos[2].setFacility("关键卡口");
        monitorInfos[3].setFacility("安全门");
        monitorInfos[4].setFacility("消防栓");
        campusSecurity.setMonitorInfo(monitorInfos);
        // 当日警情播报
        DangerBoardcast[] dangerBoardcasts = new DangerBoardcast[10];
        List<VcsWarning> warningList = vcsWarningService.list();
        int warningIndex = warningList.size() - 1;
        for (int i = 0; i< 10; i++) {
            dangerBoardcasts[i] = new DangerBoardcast();
            VcsWarning vcsWarning = warningList.get(warningIndex);
            if(warningIndex > 0){
                warningIndex--;
            } else {
                warningIndex = warningList.size() - 1;
            }
            dangerBoardcasts[i].setLevel(Integer.parseInt(vcsWarning.getWarningType()));
            dangerBoardcasts[i].setRecordTime(vcsWarning.getCreatedTime().toString());
            VcsDevice device = vcsDeviceService.getById(vcsWarning.getDeviceId());
            dangerBoardcasts[i].setRecordBayonet(device.getDName());
            dangerBoardcasts[i].setRecordCause(vcsWarning.getWarningContent());
            dangerBoardcasts[i].setHandleStatus(0);
        }
        campusSecurity.setDangerBoardcast(dangerBoardcasts);
        // 警情类型统计
        DangerStatistics[] dangerStatistics = new DangerStatistics[1];
        dangerStatistics[0] = new DangerStatistics();
        QueryWrapper<VcsWarning> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("warningType", "1");
        dangerStatistics[0].setTimeoutRemind(String.valueOf(vcsWarningService.list(queryWrapper1).size()));
        QueryWrapper<VcsWarning> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("warningType", "2");
        dangerStatistics[0].setOutUseRangeWarning(String.valueOf(vcsWarningService.list(queryWrapper2).size()));
        QueryWrapper<VcsWarning> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("warningType", "3");
        dangerStatistics[0].setRenewRemind(String.valueOf(vcsWarningService.list(queryWrapper3).size()));
        campusSecurity.setDangerStatistics(dangerStatistics);
        campusSituation.setCampusSecurity(campusSecurity);

        return ResultUtils.success(campusSituation);
    }

}
