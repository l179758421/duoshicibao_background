package com.runer.cibao.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonObject;
import com.runer.cibao.Config;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.JpushService;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/6
 **/
@Service
public class JpushServiceImpl implements JpushService {

    @Value("${app_key}")
    private String APP_KEY ;

    @Value("${master_secret}")
    private String MASTER_SECRET ;


    @Autowired
    IdsMachine idsMachine ;


    private JPushClient createJpushClient(){
        JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        return  jPushClient ;
    }
    @Override
    public PushResult pushForUser(String msg, String title, String phoneNumber, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException {
        if (extras==null){
            extras =new HashMap<>() ;
        }
        if (jsonExtras==null){
            jsonExtras =new JsonObject() ;
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(phoneNumber))
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .setSound("default")
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .build())
                .build();
        PushResult pushResult = null;
        try {
            pushResult = createJpushClient().sendPush(payload);
        } catch (APIConnectionException e) {
            System.err.println("Connection error. Should retry later. ");
            System.err.println("Sendno: " + payload.getSendno());
            throw  new SmartCommunityException(ResultMsg.JPUSH_FAILED);
        } catch (APIRequestException e) {
            System.err.println("Error response from JPush server. Should review and fix it. "+e);
            System.err.println("HTTP Status: " + e.getStatus());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Error Message: " + e.getErrorMessage());
            System.err.println("Msg ID: " + e.getMsgId());
            System.err.println("Sendno: " + payload.getSendno());
            throw  new SmartCommunityException(ResultMsg.JPUSH_FAILED);
        }
        return pushResult;
    }

    @Override
    public PushResult pushForAndroid(String msg, String title,
                                     Map<String, String> extras,
                                     JsonObject jsonExtras) throws SmartCommunityException {
        if (extras==null){
            extras =new HashMap<>() ;
        }
        if (jsonExtras==null){
            jsonExtras =new JsonObject() ;
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .build())
                .build();
        PushResult pushResult = null;
        try {
            pushResult = createJpushClient().sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new SmartCommunityException(ResultMsg.JPUSH_FAILED);
        }
        return pushResult;
    }

    @Override
    public PushResult pushForIos(String msg, String title, Map<String, String> extras,JsonObject jsonExtras)throws SmartCommunityException {
        if (extras==null){
            extras =new HashMap<>() ;
        }
        if (jsonExtras==null){
            jsonExtras =new JsonObject() ;
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .build())
                .build();
        PushResult pushResult = null;
        try {
            pushResult = createJpushClient().sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new SmartCommunityException(ResultMsg.JPUSH_FAILED);
        }
        return pushResult;
    }

    @Override
    public PushResult pushForAll(String msg, String title, Map<String, String> extras,JsonObject jsonExtras) throws SmartCommunityException {
        if (extras==null){
            extras =new HashMap<>() ;
        }
        if (jsonExtras==null){
            jsonExtras =new JsonObject() ;
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .build())
                .build();
        PushResult pushResult = null;
        try {
            pushResult = createJpushClient().sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new SmartCommunityException(ResultMsg.JPUSH_FAILED);
        }
        return pushResult;
    }

    @Override
    public PushResult pushForTag(String msg, String tag, String title, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException {


        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .build())
                .build();
        PushResult pushResult = null;
        try {
            pushResult = createJpushClient().sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new SmartCommunityException(ResultMsg.JPUSH_FAILED);
        }
        return pushResult;
    }


    //TODO  推送失败，这里有问题
    @Override
    public PushResult pushForTag(String msg, String [] alias, String title, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException {
        if (extras==null){
            extras =new HashMap<>() ;
        }
        if (jsonExtras==null){
            jsonExtras =new JsonObject() ;
        }

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(msg)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extras)
                                .addExtra(Config.JPUSH_JSON_EXTRAS_KEY, jsonExtras)
                                .build())
                        .build())
                .build();
        PushResult pushResult = null;
        try {
            pushResult = createJpushClient().sendPush(payload);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new SmartCommunityException(ResultMsg.JPUSH_FAILED);
        }
        return pushResult;
    }

}
