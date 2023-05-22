package com.ryk.vcsbyrfid.job.once;

import com.ryk.vcsbyrfid.utils.rfid.UHF.UHFReader;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 全量同步帖子到 es
 *
 * @author ryk
 * @from  
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class OpenCom implements CommandLineRunner {


    @Resource
    private UHFReader uhfReader;

    @Override
    public void run(String... args) {
//        int i = uhfReader.OpenByCom(6, (byte) 5);
//        if (i == 0) {
//            System.out.println("Connect Successfully!");
//            log.info("【系统操作】连接RFID检测器成功！");
//        }
//        if (i != 0) {
//            System.out.println("Connect failure!");
//            log.info("【系统操作】连接RFID检测器失败！");
//        }
    }
}
