package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.util.NormalUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author sww
 * @Date 2018/7/24
 * 上传Controller
 **/

@RestController
@RequestMapping(value = "upload")
public class UploadController {


    @Value("${web.upload-basePath}")
    private String rePath;

    @Value("${web.upload-path}")
    private String abPath;



    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadFile")
    public ApiResult uploadFile(MultipartFile file ){

        return  NormalUtil.saveMultiFile(file,rePath,abPath) ;
    }

    //todo other uploadApi!!



}
