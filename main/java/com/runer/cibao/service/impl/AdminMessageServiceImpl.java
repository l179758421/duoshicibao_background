package com.runer.cibao.service.impl;

import com.runer.cibao.domain.Message;
import com.runer.cibao.domain.repository.MessageRepository;
import com.runer.cibao.service.AdminMessageService;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/7/9
 **/
@Service
public class AdminMessageServiceImpl extends BaseServiceImp<Message, MessageRepository> implements AdminMessageService {
}
