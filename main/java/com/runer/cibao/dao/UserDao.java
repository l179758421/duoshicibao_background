package com.runer.cibao.dao;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.domain.User;
import com.runer.cibao.domain.repository.UserRepository;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.RolesService;
import com.runer.cibao.util.machine.IdsMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.runer.cibao.exception.ResultMsg.SUCCESS;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/4
 **/
@Repository
public class UserDao {

     @Autowired
     UserRepository userRepository ;

     @Autowired
     RolesService rolesService ;

     @Autowired
     RolesDao rolesDao ;


    /**
     * @param name
     * @return
     */
     public User userExist(String name){
         User user =  userRepository.findUserByLoginName(name);
         if (user==null){
             return  null ;
         }
         String rolesIds = user.getRolesIds() ;
         if (!StringUtils.isEmpty(rolesIds)){
             try {
                 String ids = new IdsMachine().deparseIdsToNormal(rolesIds);
                 List<Roles> roles = rolesService.findByIds(ids);
                 user.setRoles(roles);
             } catch (SmartCommunityException e) {
                 e.printStackTrace();
             }
         }
         return  user ;
     }


     public User findUserByLoginName(String name) {
      User user =  userRepository.findUserByLoginName(name);
      if (user!=null){
          generateUser(user) ;
      }
      return user ;
     }


     public User generateUser (User user ){
         String rolesIds = user.getRolesIds() ;
         if (!StringUtils.isEmpty(rolesIds)){
             try {
                 String ids = new IdsMachine().deparseIdsToNormal(rolesIds);
                 List<Roles> roles = rolesService.findByIds(ids);
                 for (Roles role : roles) {
                    rolesDao.generateRoles(role);
                 }
                 user.setRoles(roles);
             } catch (SmartCommunityException e) {
                 e.printStackTrace();
             }
         }
         return  user ;
     }


    public User getById(String id) {
        Optional<User> userOptional =userRepository.findById(Long.parseLong(id));
        if (userOptional.isPresent()){
            return  userOptional.get();
        }
        return  null;
    }
}
