package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.config.Area;
import com.runer.cibao.dao.PermissionsDao;
import com.runer.cibao.domain.Member;
import com.runer.cibao.domain.Permission;
import com.runer.cibao.domain.Person;
import com.runer.cibao.domain.Roles;
import com.runer.cibao.service.PermisionsService;
import com.runer.cibao.service.UserLoginService;
import com.runer.cibao.util.TestForExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author sww
 * 2019-09-24
 * 13:51
 **/
@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    Area area ;

    @Autowired
    TestForExcel testForExcel ;

    @Autowired
    PermisionsService permisionsService ;

    @Autowired
    PermissionsDao permissionsDao ;

    @Autowired
    UserLoginService userLoginService ;




    @RequestMapping("excel")
    public void downExcel(HttpServletResponse response){
        List<Person> personList = new ArrayList<>();
//        Person person1 = new Person("wer","1",new Date());
//        Person person2 = new Person("werwa","2", DateUtils.addDays(new Date(),3));
//        Person person3 = new Person("werwe","1", DateUtils.addDays(new Date(),10));
//        Person person4 = new Person("fsdfsd","1", DateUtils.addDays(new Date(),-10));
//        personList.add(person1);
//        personList.add(person2);
//        personList.add(person3);
//        personList.add(person4);
    }

    @RequestMapping("main")
    public String main(){
        return "main" ;
    }

}
