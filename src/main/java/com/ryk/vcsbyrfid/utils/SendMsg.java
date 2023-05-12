package com.ryk.vcsbyrfid.utils;


import com.google.gson.Gson;
import com.ryk.vcsbyrfid.model.entity.LicensePlate;
import com.ryk.vcsbyrfid.model.entity.SmsRecord;
import com.ryk.vcsbyrfid.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.ryk.vcsbyrfid.constant.CommonConstant.*;


/**
 * 发送短信
 */
@Component
public class SendMsg {
    /**
     * 发送短信至目标手机号
     * @param phoneNum 手机号
     * @param templateId 模板类型
     * @param code 验证码
     * @param time 时间或者时段
     * @param place 地点
     * @param carLicense 车牌号
     * @return 发送结果
     */
    public String sendMegToUser(String phoneNum, String templateId, String code, String time, String place, String carLicense) {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APPCODE);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phoneNum);
        querys.put("smsSignId", "eb90eeaa53e94365a338f5b3ac10f8cb");
        querys.put("templateId", templateId);
        switch (templateId) {
            case SEND_CODE_TEMPLATE_ID:
                querys.put("param", "**code**:" + code);
                break;
            case SEND_WARNING_ABNORMAL_TIME_TEMPLATE_ID:
                querys.put("param", "**carID**:" + carLicense + ",**place**:" + place + ",**time**:" + time);
                break;
            case SEND_REMIND_LACK_TEMPLATE_ID:
                querys.put("param", "**carID**:" + carLicense + ",**time**:" + time + ",**leftday**:" + "7");
                break;
            case SEND_WARNING_NO_RECORDS_TEMPLATE_ID:
                querys.put("param", "**carID**:" + carLicense + ",**place**:" + place + ",**hour**:" + time);
                break;
            case SEND_WARNING_SENSITIVE_DETECT_TEMPLATE_ID:
                querys.put("param", "**carID**:" + carLicense);
                break;
            default:
                break;
        }


//        if (templateId == SEND_CODE_TEMPLATE_ID) {
//            querys.put("param", "**code**:" + code);
//            querys.put("smsSignId", "eb90eeaa53e94365a338f5b3ac10f8cb");
//            querys.put("templateId", templateId);
//        } else if (templateId == SEND_WARNING_TEMPLATE_ID) {
//            querys.put("param", "**carID**:" + carLicense + ",**place**:" + place + ",**time**:" + time);
//            querys.put("smsSignId", "eb90eeaa53e94365a338f5b3ac10f8cb");
//            querys.put("templateId", templateId);
//        }
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");
            Gson gson = new Gson();
            SmsRecord myResponse = gson.fromJson(json, SmsRecord.class);
            if (myResponse.getCode() == 0) {
                return "发送成功";
            }
            return "发送失败";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
