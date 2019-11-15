package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.HelpToUser;
import com.runer.cibao.domain.repository.HelpToUserRepository;
import org.springframework.data.domain.Page;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface HelpToUserService extends BaseService<HelpToUser, HelpToUserRepository>{

    /**
     * 获得helper的列表
     * @param theme
     * @param page
     * @param limit
     * @return
     */
    Page<HelpToUser> findHelpToUsers(String theme, Integer page, Integer limit) ;

    /**
     * 增加或者更新helper ；
     * @param id
     * @param theme
     * @param content
     * @param userId
     * @param subContent
     * @return
     */
    ApiResult addOrUpdateHelptoUser(Long id, String theme, String content, Long userId, String subContent);

}
