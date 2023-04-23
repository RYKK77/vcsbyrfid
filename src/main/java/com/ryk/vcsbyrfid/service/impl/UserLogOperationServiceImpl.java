package com.ryk.vcsbyrfid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryk.vcsbyrfid.mapper.UserLogOperationMapper;
import com.ryk.vcsbyrfid.model.entity.UserLogOperation;
import com.ryk.vcsbyrfid.service.UserLogOperationService;
import org.springframework.stereotype.Service;

/**
* @author RYKK
* @description 针对表【user_log_operation(日志关联)】的数据库操作Service实现
* @createDate 2023-04-22 22:20:17
*/
@Service
public class UserLogOperationServiceImpl extends ServiceImpl<UserLogOperationMapper, UserLogOperation> implements UserLogOperationService {

}




