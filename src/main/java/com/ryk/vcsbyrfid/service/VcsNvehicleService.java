package com.ryk.vcsbyrfid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ryk.vcsbyrfid.model.entity.VcsNvehicle;
import com.ryk.vcsbyrfid.model.vo.VcsNvehicleVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author RYKK
* @description 针对表【vcs_nvehicle(车辆)】的数据库操作Service
* @createDate 2023-04-22 22:36:24
*/
public interface VcsNvehicleService extends IService<VcsNvehicle> {
    /**
     * 获取当前名下车辆信息
     * @param request
     * @return
     */
    public VcsNvehicleVO getVehicleInfo(HttpServletRequest request);

    /**
     * 更改车辆正常使用时间
     * @param vcsNvehicle
     * @param request
     * @return
     */
    public Boolean updateVehicleTime(VcsNvehicle vcsNvehicle, HttpServletRequest request);

    /**
     * 更改车辆状态
     * @param vcsNvehicle
     * @param request
     * @return
     */
    public Boolean updateVehicleStatus(VcsNvehicle vcsNvehicle, HttpServletRequest request);
}
