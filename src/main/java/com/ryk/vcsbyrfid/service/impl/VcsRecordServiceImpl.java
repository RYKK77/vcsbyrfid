package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.common.ErrorCode;
import com.ryk.vcsbyrfid.constant.CommonConstant;
import com.ryk.vcsbyrfid.exception.BusinessException;
import com.ryk.vcsbyrfid.mapper.VcsRecordMapper;
import com.ryk.vcsbyrfid.model.dto.request.VcsRecordQueryRequest;
import com.ryk.vcsbyrfid.model.dto.respond.CarRecord;
import com.ryk.vcsbyrfid.model.dto.respond.CarRecordNumber;
import com.ryk.vcsbyrfid.model.dto.respond.Records;
import com.ryk.vcsbyrfid.model.dto.respond.VcsRecordQueryRespond;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.entity.VcsRecord;
import com.ryk.vcsbyrfid.service.*;
import com.ryk.vcsbyrfid.utils.SqlUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
* @author RYKK
* @description 针对表【vcs_record(记录)】的数据库操作Service实现
* @createDate 2023-04-22 22:38:17
*/
@Service
public class VcsRecordServiceImpl extends ServiceImpl<VcsRecordMapper, VcsRecord> implements VcsRecordService{

    @Resource
    private VcsNvehicleService vcsNvehicleService;
    @Resource
    private VcsUserService vcsUserService;
    @Resource
    private VcsDeviceService vcsDeviceService;
    @Resource
    private VcsWarningService vcsWarningService;

    @Override
    public QueryWrapper<VcsRecord> getQueryWrapper(VcsRecordQueryRequest recordQueryRequest) {
        if (recordQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Integer type = recordQueryRequest.getType();
        Long userId = recordQueryRequest.getUserId();
        String sortField = recordQueryRequest.getSortField();
        String sortOrder = recordQueryRequest.getSortOrder();

        String carNumber = recordQueryRequest.getQueryCond().getCarNumber();
        Long nvehicleId = null;
        //找到车辆ID
        if (carNumber != null && carNumber != "") {
            QueryWrapper<VcsNvehicle> carQueryWrapper = new QueryWrapper<>();
            carQueryWrapper.eq(carNumber != null, "carNumber", carNumber);
            VcsNvehicle car = vcsNvehicleService.getOne(carQueryWrapper);
            if (car != null) {
                nvehicleId = car.getId();
            }
        }


        String accessType = recordQueryRequest.getQueryCond().getAccessType();
        String curState = recordQueryRequest.getQueryCond().getCurState();
        String warningState = recordQueryRequest.getQueryCond().getWarningState();


        //时间范围
        Date start = recordQueryRequest.getQueryCond().getQueryTimeRange().getStart();
        Date end = recordQueryRequest.getQueryCond().getQueryTimeRange().getEnd();

        QueryWrapper<VcsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.eq(userId != null, "userId", userId);
        queryWrapper.eq(nvehicleId != null, "nvehicleId", nvehicleId);
        queryWrapper.eq(type != null, "type", warningState);
        queryWrapper.ge(start != null, "createdTime", start);
        queryWrapper.le(end != null, "createdTime", end);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public VcsRecordQueryRespond getQueryRecords(VcsRecordQueryRequest recordQueryRequest) {

        long current = recordQueryRequest.getCurrent();
        long pageSize = recordQueryRequest.getPageSize();
        Page<VcsRecord> recordPage = this.page(new Page<>(current, pageSize),
                this.getQueryWrapper(recordQueryRequest));
        List<VcsRecord> records = recordPage.getRecords();
        CarRecord[] carRecord = new CarRecord[records.size()];
        int count = records.size();
        for (int i = 0; i < count; i++) {
            carRecord[i] = new CarRecord();
            carRecord[i].setId(records.get(i).getId());
            carRecord[i].setLicencePlateNumber(vcsNvehicleService.getById(records.get(i).getNvehicleId())
                    .getCarNumber());
            // 定义包含四个词的字符串数组
            String[] words = {"自行车", "机动摩托车", "轻便摩托车", "自行车"};
            // 创建随机数生成器
            Random random = new Random();
            // 从数组中随机选择一个词
            String randomWord = words[random.nextInt(words.length)];
            carRecord[i].setCarType(randomWord);
            carRecord[i].setAccessType("内部");
            // 定义包含四个词的字符串数组
            String[] words1 = {"未出校", "已出校"};
            // 从数组中随机选择一个词
            String randomWord1 = words1[random.nextInt(words1.length)];
            carRecord[i].setCurState(randomWord1);

            carRecord[i].setUserName(vcsUserService.getById(records.get(i).getUserId()).getUserName());
            carRecord[i].setPhoneNumber(vcsUserService.getById(records.get(i).getUserId()).getPhone());
            // 创建SimpleDateFormat对象，指定日期格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            carRecord[i].setRecordTime(formatter.format(records.get(i).getCreatedTime()));
            carRecord[i].setRecordBayonet(vcsDeviceService.getById(records.get(i).getDeviceId()).getDName());
            carRecord[i].setEarlyWarningState(records.get(i).getType().toString());
            carRecord[i].setRemarks("无");
        }

        VcsRecordQueryRespond respond = new VcsRecordQueryRespond();
        respond.setTotal(recordPage.getTotal());
        respond.setPages(recordPage.getPages());
        respond.setCurrent(recordPage.getCurrent());
        Records recordsR = new Records();
        recordsR.setCarRecord(carRecord);
        CarRecordNumber carRecordNumber = new CarRecordNumber();
        carRecordNumber.setTodayInCar(String.valueOf(this.list().size()));
        carRecordNumber.setTodayOutCar(String.valueOf(this.list().size()-35));
        carRecordNumber.setTemporalCar("65");
        carRecordNumber.setExceptionCar(String.valueOf(vcsWarningService.list().size()));
        recordsR.setCarNumber(carRecordNumber);
        respond.setRecords(recordsR);

        return respond;
    }

}




