package com.runer.cibao.api;

import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.PageApiResult;
import com.runer.cibao.domain.MessageRead;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.MessageService;
import com.runer.cibao.util.NormalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/9
 **/
@RestController
@RequestMapping(value = "api/messageApi")
@Api(description = "message相关")
public class MessageApi {



    @Autowired
    MessageService messageService ;

    @ApiOperation(value = "获得消息列表",notes = "获得消息列表")
    @RequestMapping(value = "getUserMessage",method = RequestMethod.POST)
    public PageApiResult<MessageRead> getMesssges(Long userId ,Integer page , Integer type ,Integer isRead ,Integer limit){
        Page<MessageRead> pages = messageService.findMessageRead(userId, type, isRead, page, limit);
       return NormalUtil.createPageResult(pages);
    }
    @ApiOperation(value = "获得系统已读消息",notes = "获得系统已读消息")
    @RequestMapping(value = "getSysMessageReaded",method = RequestMethod.POST)
    public PageApiResult getSysMessageReaded(Long userId ,Integer page ,Integer limit){
        Page<MessageRead> pages = messageService.findMessageRead(userId, Config.SYSTEM_MSG, Config.READED, page, limit);
        return  NormalUtil.createPageResult(pages);
    }
    @ApiOperation(value = "获得系统未读消息",notes = "获得系统未读消息")
    @RequestMapping(value = "getSysMessageNotReaded",method = RequestMethod.POST)
    public PageApiResult getSysMessageNotReaded(Long userId ,Integer page ,Integer limit){
        Page<MessageRead> pages = messageService.findMessageRead(userId, Config.SYSTEM_MSG, Config.NOT_READ, page, limit);
        return  NormalUtil.createPageResult(pages);
    }

    @ApiOperation(value = "获得班级消息已读",notes = "获得班级消息已读")
    @RequestMapping(value = "getClassMessageReaded",method = RequestMethod.POST)
    public PageApiResult getClassMessageReaded(Long userId ,Integer page ,Integer limit){
        Page<MessageRead> pages = messageService.findMessageRead(userId, Config.CLASS_MSG, Config.READED, page, limit);
        return  NormalUtil.createPageResult(pages);
    }
    @ApiOperation(value = "获得班级消息未读",notes = "获得班级消息未读")
    @RequestMapping(value = "getClassMessageNotReaded",method = RequestMethod.POST)
    public PageApiResult getClassMessageNotReaded(Long userId ,Integer page ,Integer limit){
        Page<MessageRead> pages = messageService.findMessageRead(userId, Config.CLASS_MSG, Config.NOT_READ, page, limit);
        return  NormalUtil.createPageResult(pages);
    }


    @ApiOperation(value = "设置消息为已读",notes = "设置消息为已读")
    @RequestMapping(value = "setMessageReaded",method = RequestMethod.POST)
    public ApiResult getClassMessageNotReaded(Long messageId){
       return   messageService.setMessageRead(messageId) ;
    }


    @ApiOperation(value = "删除消息",notes = "删除消息")
    @RequestMapping(value = "deleteMessage",method = RequestMethod.POST)
    public ApiResult deleteMessage(Long messageId){
        try {
               messageService.deleteById(messageId) ;
               return  new ApiResult(ResultMsg.SUCCESS,messageId) ;
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return new ApiResult(e.getResultMsg(),null) ;
        }
    }

    @ApiOperation(value = "获得消息详情",notes = "获得消息详情")
    @RequestMapping(value = "getMessageDetail",method = RequestMethod.POST)
    public ApiResult getMessageDetail(Long messageId){
    return     messageService.findByIdWithApiResult(messageId);
    }

    @ApiOperation(value = "获得系统消息的未读数量",notes = "获得系统消息的未读数量")
    @RequestMapping(value = "getSysMessageCount",method = RequestMethod.POST)
    public ApiResult getSysMessageCount(Long userId){
          long count =   messageService.coutMessageNotRead(userId, Config.SYSTEM_MSG);
        Map<String, Object> results = NormalUtil.generateMapData(data -> {
            data.put("count", count);
        });
        return  new ApiResult(ResultMsg.SUCCESS,results) ;

    }
    @ApiOperation(value = "获得班级信息的未读数量",notes = "获得班级信息的未读数量")
    @RequestMapping(value = "getclassMessageCount",method = RequestMethod.POST)
    public ApiResult getclassMessageCount(Long userId){
        long count =   messageService.coutMessageNotRead(userId, Config.CLASS_MSG);
        Map<String, Object> results = NormalUtil.generateMapData(data -> {
            data.put("count", count);
        });
        return  new ApiResult(ResultMsg.SUCCESS,results) ;

    }



















}
