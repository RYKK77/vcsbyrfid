package com.ryk.vcsbyrfid.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ryk.vcsbyrfid.model.dto.request.VcsUserAddRequest;
import com.ryk.vcsbyrfid.service.VcsUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import javax.annotation.RegEx;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * EasyExcel 测试
 */
@SpringBootTest
public class EasyExcelTest {
    @Resource
    private VcsUserService vcsUserService;

    @Test
    public void doImport() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:testE.xlsx");
//        List<Map<Integer, String>> list = EasyExcel.read(file)
//                .excelType(ExcelTypeEnum.XLSX)
//                .sheet()
//                .headRowNumber(0)
//                .doReadSync();
//        System.out.println(list);
        EasyExcel.read(file, VcsUserAddRequest.class, new ExcelListener(vcsUserService)).sheet(0).doRead();
    }

}