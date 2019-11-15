package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/7
 **/
public interface AppUserRepository extends JpaRepository<AppUser,Long> ,JpaSpecificationExecutor<AppUser> {

    AppUser findAppUserByPhoneNum(String phoneNum) ;

    AppUser findAppuserByName(String name);

    AppUser findAppUserByUid(String uid);

    /**
     * 根据schooluid查找
     * @param schoolUid
     * @return
     */
    List<AppUser> findAppUserBySchoolId(String schoolUid);

    List<AppUser> findAppUserByClassInSchoolId(Long classId);

}
