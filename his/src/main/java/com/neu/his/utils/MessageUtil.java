package com.neu.his.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

/**
 * Copyright: Copyright (c) 2022 msprc.li
 *
 * @ClassName: Result.java
 * @Description: 发送验证短信
 * @version: v1.0.0
 * @author: msprc
 * @date: 2022年12月8日 上午9:03:23
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022年12月8日     msprc           v1.0.0               修改原因
 */
public class MessageUtil {
    /**
     * 向指定手机号发送验证码
     *
     * @param phoneNumber 目标手机号
     * @param code        验证码
     * @return 0正常发送，-1发送失败
     */
    public static int sendMessage(String phoneNumber, String code) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDxjESR9ZibDYcPwCBtLVOzKOAftrYy7za", "LPDjywc2B3eyIL7bpmRpwmKbcLnxqSos");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {phoneNumber};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppId("1400777671");
            req.setSignName("燕山园");
            req.setTemplateId("1632254");

            String[] templateParamSet1 = {code};
            req.setTemplateParamSet(templateParamSet1);

            req.setSessionContext("shortMessage");
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println("短信发送成功！" + SendSmsResponse.toJsonString(resp));
            return 0;
        } catch (TencentCloudSDKException e) {
            System.out.println("短信发送失败：" + e);
            return -1;
        }
    }

    public static void main(String[] args) {
        int i = MessageUtil.sendMessage("15243456759", "123456");
        if (i == 0) {
            System.out.println("成功！");
        } else {
            System.out.println("失败！");
        }
    }
}
