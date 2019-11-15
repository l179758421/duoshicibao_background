package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.HelpToUserDao;
import com.runer.cibao.domain.HelpToUser;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.HelpToUserRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.HelpToUserService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/

@Service
public class HelpToUserServiceImpl extends BaseServiceImp<HelpToUser, HelpToUserRepository> implements HelpToUserService {


    @Autowired
    HelpToUserDao helpToUserDao ;

    @Override
    public Page<HelpToUser> findHelpToUsers(String theme, Integer page, Integer limit) {
        return helpToUserDao.findHelpToUser(theme,PageableUtil.basicPage(page,limit));
    }

    @Override
    public ApiResult addOrUpdateHelptoUser(Long id, String theme, String content, Long userId,String subContent) {

        HelpToUser helpToUser =new HelpToUser() ;
        if (id!=null){
            try {
                helpToUser =findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(e.getResultMsg(),null) ;
            }
        }
        helpToUser.setId(id);
        helpToUser.setTheme(theme);
        helpToUser.setContent(content);
        helpToUser.setSubContent(subContent);

        User user =new User();
        user.setId(userId);

        helpToUser.setCreateUser(user);


        if (id==null) {
            helpToUser.setCreateTime(new Date());
        }
        if (userId==null){
            return  new ApiResult(ResultMsg.USER_ID_IS_NOT_ALLOWED_NULL,null);
        }
        helpToUser=r.save(helpToUser);
        return new ApiResult(ResultMsg.SUCCESS,helpToUser);
    }






}
