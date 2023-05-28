package com.ryk.vcsbyrfid.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ryk.vcsbyrfid.model.dto.request.VcsUserAddRequest;
import com.ryk.vcsbyrfid.model.entity.VcsUser;
import com.ryk.vcsbyrfid.service.VcsUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelListener extends AnalysisEventListener<VcsUserAddRequest> {

    //创建list集合封装最终的数据
    List<VcsUserAddRequest> list = new ArrayList<>();
    // list中每达到10条数据就存储数据库，然后清理list ，方便内存回收
    // 实际使用中可以根据服务器性能设置更多条
    private static final int BATCH_COUNT = 10;

    // 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。
    // 如果不用存储从Excel文件中读取的数据，那么这个对象就没用
    private VcsUserService vcsUserService;

    // 无参构造
    public ExcelListener() {
    }

    // 有参构造 可以在每次创建Listener对象的时候需要把spring管理的类传进来
    public ExcelListener(VcsUserService vcsUserService) {
        this.vcsUserService = vcsUserService;
    }

    //读取excel表头信息时执行
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    // 读取excel内容信息时执行
    // EasyExcel会会一行一行去读取excle内容，每解析excel文件中的一行数据，都会调用一次invoke方法
    @Override
    public void invoke(VcsUserAddRequest data, AnalysisContext context) {
        System.out.println("***" + data);
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            List<VcsUser> listInsert = new ArrayList<>();
            for (int i = 0; i < BATCH_COUNT; i++) {
                VcsUser user = new VcsUser();
                BeanUtils.copyProperties(list.get(i), user);
                //默认密码为1234567890
                user.setPassword(DigestUtils.md5DigestAsHex(("rykk" + "1234567890").getBytes()));
                listInsert.add(user);
            }
            vcsUserService.saveBatch(listInsert);
            // 存储完成清理 list
            list = new ArrayList<VcsUserAddRequest>(BATCH_COUNT);
        }
    }


    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        List<VcsUser> listInsert = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            VcsUser user = new VcsUser();
            BeanUtils.copyProperties(list.get(i), user);
            //默认密码为1234567890
            user.setPassword(DigestUtils.md5DigestAsHex(("rykk" + "1234567890").getBytes()));
            listInsert.add(user);
        }
        vcsUserService.saveBatch(listInsert);
        list = new ArrayList<VcsUserAddRequest>(BATCH_COUNT);
    }
}
