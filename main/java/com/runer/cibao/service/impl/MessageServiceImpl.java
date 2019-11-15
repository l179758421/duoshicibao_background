package com.runer.cibao.service.impl;

import com.google.gson.JsonObject;
import com.runer.cibao.Config;
import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.MessageDao;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.MessageReadRepository;
import com.runer.cibao.domain.repository.MessageRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.*;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/5
 **/
@Service
public class MessageServiceImpl extends BaseServiceImp<MessageRead, MessageReadRepository> implements MessageService {


    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageDao messageDao ;

    @Autowired
    UserService userService ;

    @Autowired
    AppUserService appUserService ;

    @Autowired
    ClassInSchoolService classInSchoolService ;


    @Autowired
    JpushService jpushService ;

    @Autowired
    CommentService commentService;


    /**
     * 小的工具类
     * @param appUsers
     * @param message
     * @return
     */
    private List<MessageRead> generateMessageRead(List<AppUser> appUsers , Message message ){

        List<MessageRead> messageReads =new ArrayList<>() ;
        appUsers.forEach(user -> {
            MessageRead messageRead =new MessageRead() ;
            messageRead.setAppUser(user);
            messageRead.setMessage(message);
            messageRead.setState(Config.NOT_READ);
            messageReads.add(messageRead);
        });
        return  messageReads ;
    }


    private MessageRead generateMessageReadOne(AppUser appUser , Message message ){
            MessageRead messageRead =new MessageRead() ;
            messageRead.setAppUser(appUser);
            messageRead.setMessage(message);
            messageRead.setState(Config.NOT_READ);
        return  messageRead ;
    }

    @Override
    public ApiResult generateSystemMessage(String msgContent , String title , String url , Integer type , Long relatedId , Long sendUserId) {




        ApiResult messageResult =createSystemMessage(msgContent,title,url,type,relatedId,sendUserId) ;



        if (messageResult.isFailed()){
            return  messageResult ;
        }
        Message message = (Message) messageResult.getData();
        List<AppUser> users = appUserService.findAll();
        List<MessageRead> messageReads =generateMessageRead(users,message);
        r.saveAll(messageReads);



        JsonObject jsonObject =new JsonObject() ;
        jsonObject.addProperty("id",message.getId());
        jsonObject.addProperty("type",0);

        try {
            jpushService.pushForAll(msgContent,title,null,jsonObject) ;



            logger.error("推送成功！！");
            message.setPushState(Config.PSUH_SUCCESS);
            messageRepository.save(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new ApiResult(ResultMsg.SUCCESS,null);
    }

    @Override
    public ApiResult generateStudentMessage(String msgContent , String title , String url , Long classInSchoolId, Integer type , Long relatedId , Long sendUserId) {
            try {
                if(relatedId != null) {
                    Long appuserId = commentService.findById(relatedId).getUserId();
                    ApiResult messageResult =createTypedMessage(msgContent,title,url,classInSchoolId,type,relatedId,sendUserId);
                    if (messageResult.isFailed()){
                        return  messageResult ;
                    }

                    Message message = (Message) messageResult.getData();
                    AppUser users = appUserService.findById(appuserId);
                    MessageRead messageRead =generateMessageReadOne(users,message);
                    r.save(messageRead);

                    JsonObject jsonObject =new JsonObject() ;
                    jsonObject.addProperty("id",message.getId());
                    jsonObject.addProperty("type", Config.CLASS_MSG);
                    jpushService.pushForUser(msgContent,title,String.valueOf(appuserId),null,jsonObject);
                    logger.error("推送成功！！");
                    message.setPushState(Config.PSUH_SUCCESS);
                    messageRepository.save(message);
                }
            } catch (SmartCommunityException e) {
                e.printStackTrace();
            }
        return new ApiResult(ResultMsg.SUCCESS,null);
    }


    @Override
    public ApiResult generateClassMessage(String msgContent, String title, String url, Integer type, Long relatedId, Long classInschoolId, Long semdUserId) {

        ApiResult messageResult =createClassMessage(msgContent,title,url,type,relatedId,classInschoolId,semdUserId) ;
            if (messageResult.isFailed()){
                return  messageResult ;
            }
            Message message= (Message) messageResult.getData();

            List<AppUser> appUsers = appUserService.findAppUsers(null, null, classInschoolId, null,null,null, null,1, Integer.MAX_VALUE).getContent();;
            List<MessageRead> messageReads = generateMessageRead(appUsers,message);
            r.saveAll(messageReads);
            try {
                jpushService.pushForTag(msgContent,String.valueOf(classInschoolId),title,null,null);
                logger.error("推送成功！！");
                message.setPushState(Config.PSUH_SUCCESS);
                messageRepository.save(message);
            } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new ApiResult(ResultMsg.SUCCESS,null);
    }

    @Override
    public ApiResult setMessageRead(Long messageReadId) {

        ApiResult messageResult =findByIdWithApiResult(messageReadId) ;
        if (messageResult.isFailed()){
            return  messageResult ;
        }
        MessageRead message = (MessageRead) messageResult.getData();
        message.setState(Config.READED);
        message =r.save(message);

        return new ApiResult(ResultMsg.SUCCESS,message);
    }

    @Override
    public ApiResult deleteMessage(Long messageReadId) {
        ApiResult messageResult =findByIdWithApiResult(messageReadId) ;
        if (messageResult.isFailed()){
            return  messageResult ;
        }
        MessageRead message = (MessageRead) messageResult.getData();
        r.delete(message);
        return new ApiResult(ResultMsg.SUCCESS,messageReadId);
    }

    @Override
    public Page<MessageRead> findMessageRead(Long appUserId, Integer type, Integer isRead, Integer page, Integer limit) {
        return messageDao.findMessage(appUserId,type,isRead,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult createClassMessage(String msgContent, String title, String url, Integer type, Long relatedId, Long classInschoolId, Long semdUserId) {
      ApiResult messageResult =createTypedMessage(msgContent,title,url,classInschoolId,type,null,semdUserId);
        return messageResult;
    }
    @Override
    public ApiResult createSystemMessage(String msgContent, String title, String url, Integer type, Long relatedId, Long sendUserId) {

       ApiResult messageResult =createTypedMessage(msgContent,title,url,null,type,null,sendUserId);

        return  messageResult ;

    }

    @Override
    public ApiResult createTypedMessage(String msgContent, String title, String url, Long classInSchoolId,
                                        Integer msgType, Long relatedId, Long sendUserId) {
         Message message =new Message() ;
         message.setMsgContent(msgContent);
         message.setMsgType(msgType);
         message.setUrl(url);
         message.setTitle(title);


        ApiResult userResult =userService.findByIdWithApiResult(sendUserId) ;
         if (userResult.isFailed()){
             return  userResult ;
         }
         User user = (User) userResult.getData();
         message.setSendUser(user);

         if(classInSchoolId!=null){
             ApiResult classResult =classInSchoolService.findByIdWithApiResult(classInSchoolId);
             if(classResult.isFailed()){
                 return  classResult ;
             }
             ClassInSchool classInSchool = (ClassInSchool) classResult.getData();
             message.setClassInSchool(classInSchool) ;
         }
         message.setCreateDate(new Date());
         message =  messageRepository.saveAndFlush(message);
        return new ApiResult(ResultMsg.SUCCESS,message);
    }

    @Override
    public Page<Message> findAdminMessages(Long userId, Integer state, Integer type , String content , Long classInschoolId, Integer page, Integer limit) {
        return messageDao.findMessageAdmin(userId,content,classInschoolId,state,type,PageableUtil.basicPage(page,limit));
    }

    @Override
    public long coutMessageNotRead(Long userId, Integer type) {
        return messageDao.coutMessageNotReaded(userId,type);
    }


}
