package com.runer.cibao.api;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.exception.ResultMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 **/
@RestController
@RequestMapping(value = "api/excel")
public class ExcelApi {


    @RequestMapping(value = "downLoad",method = RequestMethod.POST)
    public Object downLoad(HttpServletResponse response){


//        }
        return  new ApiResult(ResultMsg.SUCCESS,null) ;
    }
}
