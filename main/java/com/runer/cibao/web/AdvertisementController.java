package com.runer.cibao.web;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.base.LayPageResult;
import com.runer.cibao.domain.Advertisement;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AdvertisementService;
import com.runer.cibao.util.NormalUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

/**
 * @Author sww
 * @Date 2019/9/24
 **/
@RestController
@RequestMapping(value = "ad")
public class AdvertisementController {

    @Value("${web.upload-cibaoPath}")
    private String rePath;

    @Value("${web.upload-path}")
    private String abPath;

    @Autowired
    AdvertisementService beanService;

    @RequestMapping(value = "data_list")
    public LayPageResult<Advertisement> getDataList(String title, Integer type, Integer page, Integer limit) {
        Page<Advertisement> datas = beanService.findAllAds2(title,type,false,page,limit);

//        ApiResult result = beanService.findAllAds(title, type, false);
//        List<Advertisement> datas = (List<Advertisement>) result.getData();
        for (Advertisement data : datas) {
            if (data.getEndTime() != null) {
                if (data.getEndTime().before(new Date())) {
                    data.setOutDate(true);
                }
            }
        }
//        LayPageResult<Advertisement> pageResult = new LayPageResult<>();
//        pageResult.setCode(0);
//        pageResult.setCount(datas == null ? 0 : datas.size());
//        pageResult.setData(datas);
//        return pageResult;


        return NormalUtil.createLayPageReuslt(datas) ;
    }

    @RequestMapping(value = "addOrUpdateData")
    public ApiResult addOrUpdateData(Long id, String title, String url,
                                     Long relatedId, Integer type, String imgUrl, String dateRange) {
        Date startDate = null;
        Date endDate = null;
        if (!StringUtils.isEmpty(dateRange)) {
            String[] datas = dateRange.split(" ");
            try {
                startDate = DateUtils.parseDate(datas[0], "yyyy-MM-dd");
                endDate = DateUtils.parseDate(datas[2], "yyyy-MM-dd");
                if(startDate.getTime() > endDate.getTime()){
                    return new ApiResult("开始时间超出结束时间,请重新选择起止时间!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return beanService.addOrUpdateAd(id, title, url, relatedId, type, null, imgUrl, startDate, endDate);
    }

    @RequestMapping("deleteByIds")
    public ApiResult deleteByIds(String ids) {
        return NormalUtil.deleteByIds(beanService, ids);
    }

    @RequestMapping("getById")
    public ApiResult getById(Long id) {
        return beanService.getById(id);
    }


    @RequestMapping("deleteById")
    public ApiResult deleteById(Long id) {
        return NormalUtil.deleteById(beanService, id);
    }


    @RequestMapping(value = "forceToTop")
    public ApiResult fordceToTop(Long id) {
        return beanService.setOneToBiggest(id);
    }


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadFile")
    public ApiResult uploadFile(MultipartFile file, String type) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        if (type.equals("1")) {
            if ( (738/288-0.1) >= (width/height) || (width/height) >= (738/288+0.1)) {
                return new ApiResult("图片尺寸应为738px:288px");
            }
        }else if(type.equals("2")){
            if ((768/250-0.1) >= (width/height) || (width/height) >= (768/250+0.1)) {
                return new ApiResult("图片尺寸应为768px:250px");
            }
        }else{
            if ((768/1024-0.1) >= (width/height) || (width/height) >= (768/1024+0.1)) {
                return new ApiResult("图片尺寸应为768px:1024px");
            }
        }
        return NormalUtil.saveMultiFile(file, rePath, abPath);
    }


}
