package com.ryk.vcsbyrfid.msgSend;


import com.google.gson.Gson;
import com.ryk.vcsbyrfid.model.entity.LicensePlate;
import com.ryk.vcsbyrfid.model.entity.SmsRecord;
import com.ryk.vcsbyrfid.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

import static com.ryk.vcsbyrfid.constant.CommonConstant.*;


/**
 * 发送短信
 */
public class SendMsg {
    public String sendMegToUser(String phoneNum, String templateId, String code, String time, String place, String carLicense) {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = MSG_APPCODE;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phoneNum);

        if (templateId == SEND_CODE_TEMPLATE_ID) {
            querys.put("param", "**code**:" + code);
            querys.put("smsSignId", "eb90eeaa53e94365a338f5b3ac10f8cb");
            querys.put("templateId", templateId);
        } else if (templateId == SEND_WARNING_TEMPLATE_ID) {
            querys.put("param", "**carID**:" + carLicense + ",**place**:" + place + ",**time**:" + time);
            querys.put("smsSignId", "eb90eeaa53e94365a338f5b3ac10f8cb");
            querys.put("templateId", templateId);
        }
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
