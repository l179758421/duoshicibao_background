package com.runer.cibao.code;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * 2018-04-16
 * 15:09
 * @Descriptionsbaby_photos== SendCode
 * 网易云信发送短信
 **/
@Component
public class SendCode {

    //发送验证码的请求路径URL
    @Value("${code.yun-url}")
    private String SERVER_URL;
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    @Value("${code.yun-app-key}")
    private String APP_KEY;
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    @Value("${code.yun-app-secret}")
    private String APP_SECRET;

    //随机数
    private final String NONCE = "123456";
    //短信模板ID
    @Value("${code.yun-code-templateid}")
    private String TEMPLATEID;
    //手机号
    private final String MOBILE = "15610106172";
    //验证码长度，范围4～10，默认为4
    @Value("${code.yun-code-len}")
    private String CODELEN;

    /**
     * 发送验证码；
     *
     * @param phoneNum
     * @return
     * @throws Exception
     */
    public String sendCodeToPhone(String phoneNum) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
        nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
        nvps.add(new BasicNameValuePair("mobile", MOBILE));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
}
