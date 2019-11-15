package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Message;
import com.runer.cibao.domain.MessageRead;
import com.runer.cibao.domain.repository.MessageReadRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/5
 **/
public interface MessageService extends BaseService<MessageRead, MessageReadRepository> {


    /**
     * 生成系统消息；
     * @return
     */
    ApiResult generateSystemMessage(String msgContent, String title, String url, Integer type, Long relatedId, Long sendUserId);


    /**
     * 生成特定消息 发送
     * @param msgContent
     * @param title
     * @param url
     * @param type
     * @param relatedId
     * @param sendUserId
     * @return
     */
    ApiResult generateStudentMessage(String msgContent, String title, String url, Long classInSchoolId, Integer type, Long relatedId, Long sendUserId);

    /**
     * 生成班级信息；
     * @param msgContent
     * @param title
     * @param url
     * @param type
     * @param relatedId
     * @param classInschoolId
     * @param semdUserId
     * @return
     */
    ApiResult generateClassMessage(String msgContent, String title, String url, Integer type, Long relatedId, Long classInschoolId, Long semdUserId);

    /**
     * 设置信息已读
     * @param messageReadId
     * @return
     */
    ApiResult setMessageRead(Long messageReadId) ;

    /**
     * 删除消息
     * @param messageReadId
     * @return
     */
    ApiResult deleteMessage(Long messageReadId) ;

    /**
     * 获得信息列表；
     * @param appUserId
     * @param type
     * @param isRead
     * @param page
     * @param limit
     * @return
     */
    Page<MessageRead> findMessageRead(Long appUserId, Integer type, Integer isRead, Integer page, Integer limit) ;


    /**
     * 创建班级信息
     * @param msgContent
     * @param classInschoolId
     * @return
     */
    ApiResult createClassMessage(String msgContent, String title, String url, Integer type,
                                 Long relatedId, Long classInschoolId, Long sendUserId);

    /**
     * 创建系统消息
     * @param msgContent
     * @param sendUserId
     * @return
     */
    ApiResult createSystemMessage(String msgContent, String title, String url, Integer type, Long relatedId, Long sendUserId);

    /**
     * 创建特定的消息；
     * @param msgContent
     * @param classInSchoolId
     * @param msgType
     * @param relatedId
     * @param sendUserId
     * @return
     */
    ApiResult createTypedMessage(String msgContent, String title, String url, Long classInSchoolId, Integer msgType,
                                 Long relatedId, Long sendUserId);


    /**
     * 查找信息 （后台人员）
     * @param userId
     * @param state
     * @param classInschoolId
     * @param page
     * @param limit
     * @return
     */
    Page<Message> findAdminMessages(Long userId, Integer state, Integer type, String content, Long classInschoolId, Integer page, Integer limit);


    /**
     * 标记消息未读的数量
     * @param userId
     * @param type
     * @return
     */
    long coutMessageNotRead(Long userId, Integer type) ;






}
