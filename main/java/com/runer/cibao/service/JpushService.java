package com.runer.cibao.service;

import cn.jpush.api.push.PushResult;
import com.google.gson.JsonObject;
import com.runer.cibao.exception.SmartCommunityException;

import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/6
 **/
public interface JpushService  {


        /**
         * 给特定的用户发送推送
         * @param msg
         * @param title
         * @param phoneNum
         * @param extras
         * @param jsonExtras
         * @return
         * @throws SmartCommunityException
         */
        PushResult pushForUser(String msg, String title, String phoneNum, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException;


    /**
     * 给android用户发送推送
     * @param msg
     * @param title
     * @param extras
     * @param jsonExtras
     * @return
     * @throws SmartCommunityException
     */
        PushResult pushForAndroid(String msg, String title, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException;


    /**
     * 给ios用户发送推送
     * @param msg
     * @param title
     * @param extras
     * @param jsonExtras
     * @return
     * @throws SmartCommunityException
     */
        PushResult pushForIos(String msg, String title, Map<String, String> extras, JsonObject jsonExtras)  throws SmartCommunityException;


    /**
     * 所有的用户发送推送
     * @param msg
     * @param title
     * @param extras
     * @param jsonExtras
     * @return
     * @throws SmartCommunityException
     */
    PushResult pushForAll(String msg, String title, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException;



    PushResult pushForTag(String msg, String alias, String title, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException;
    /**
     * 所有的用户发送推送
     * @param msg
     * @param title
     * @param extras
     * @param jsonExtras
     * @return
     * @throws SmartCommunityException
     */
    PushResult pushForTag(String msg, String[] alias, String title, Map<String, String> extras, JsonObject jsonExtras) throws SmartCommunityException;


}
