package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.UidUser;
import com.runer.cibao.domain.repository.UidUserRepository;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;

public interface UidUserService extends BaseService<UidUser, UidUserRepository> {
    /**
     * 查找uid账户
     * @param Uid
     * @param page
     * @param limit
     * @return
     */
      Page<UidUser> findUidUser(String Uid, Integer page, Integer limit);

    /**
     * 批量生成uid
     * @param num
     * @param pwd
     * @param schoolId
     * @return
     */
      ApiResult batchCreateUID(Integer num, String pwd, Long schoolId);

    /**
     * 导出uid账户
     * @param httpResponse
     * @return
     */
       ApiResult exportUid(HttpServletResponse httpResponse);
}
