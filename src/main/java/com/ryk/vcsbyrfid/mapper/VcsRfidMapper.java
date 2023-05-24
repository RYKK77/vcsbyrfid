package com.ryk.vcsbyrfid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ryk.vcsbyrfid.model.entity.VcsRfid;
import org.springframework.stereotype.Repository;

/**
* @author RYKK
* @description 针对表【vcs_rfid(RFID信息)】的数据库操作Mapper
* @createDate 2023-04-22 22:45:14
* @Entity com.ryk.vcsbyrfid.model.entity.VcsRfid
*/
@Repository
public interface VcsRfidMapper extends BaseMapper<VcsRfid> {

}




