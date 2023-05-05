package com.ryk.vcsbyrfid.job.cycle;


import com.ryk.vcsbyrfid.rfid.UHF.UHFReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    /**
     * 0.1s 执行一次
     */
    @Scheduled(fixedRate = 100)
    public void run() {
        if (uhfReader.getStatues() == 0) {
            String[] inventory = uhfReader.Inventory();
            if (inventory != null && inventory.length != 0) {
                System.out.println("find tagName:" + inventory[0]);
            }
        }
    }
}
