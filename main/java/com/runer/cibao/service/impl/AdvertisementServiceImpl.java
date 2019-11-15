package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.dao.AdvertisementDao;
import com.runer.cibao.domain.Advertisement;
import com.runer.cibao.domain.repository.AdvertisementRepository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.AdvertisementService;
import com.runer.cibao.util.page.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/23
 **/


@Service
public class AdvertisementServiceImpl extends BaseServiceImp<Advertisement, AdvertisementRepository> implements AdvertisementService {

    @Autowired
    AdvertisementDao advertisementDao ;

    @Override
    public ApiResult findAllAds(String title, Integer type ,boolean containsOutDate ) {
        List<Advertisement> ads = advertisementDao.findAds(title, type ,containsOutDate);
        Collections.sort(ads, (o1, o2) -> (o2.getOrderNum()-o1.getOrderNum()));
        return new ApiResult(ResultMsg.SUCCESS,ads);
    }

    @Override
    public Page<Advertisement> findAllAds2(String title, Integer type,boolean containsOutDate , Integer page, Integer limit) {

        return advertisementDao.findAds2(title,type,containsOutDate, PageableUtil.basicPage(page,limit));
    }

    /**
     * web.upload-cibaoPath=ciBaoAdmin/
     * web.upload-cibao=${web.upload-path}${web.upload-cibaoPath}
     * @param id
     * @param title
     * @param url
     * @param relatedId
     * @param type
     * @param orderNum
     * @param file
     * @return
     */
    @Value("${web.upload-cibaoPath}")
    private String repath ;
    @Value("${web.upload-cibao}")
    private String  abPath ;



    @Override
    public ApiResult addOrUpdateAd(Long id, String title, String url, Long relatedId, Integer type, Integer orderNum , String  imgUrl ,Date statrDate ,Date endDate ) {


        Advertisement advertisement =new Advertisement() ;

        int order =1;

        //设置排序；
        if (id==null){
            Advertisement  advertisementToCompare =getSmallerOrderNum();
            if (advertisementToCompare!=null){
                order =advertisementToCompare.getOrderNum()-1 ;
            }
            advertisement.setOrderNum(order);
            advertisement.setCreateDate(new Date());
        }

        if (id!=null){
            try {
                advertisement=  findById(id);
            } catch (SmartCommunityException e) {
                e.printStackTrace();
                return  new ApiResult(e.getResultMsg(),null);
            }
        }

        advertisement.setId(id);
        advertisement.setTitle(title);
        advertisement.setUrl(url);
        advertisement.setRelatedId(relatedId);
        advertisement.setType(type);


        advertisement.setImgUrl(imgUrl);


        if (statrDate!=null&&endDate!=null){
            advertisement.setStartTime(statrDate);
            advertisement.setEndTime(endDate);
        }


        advertisement=  r.saveAndFlush(advertisement);

        return new ApiResult(ResultMsg.SUCCESS,advertisement);
    }

    @Override
    public Advertisement getBiggestOrderNum() {
       ApiResult apiResult = findAllAds(null,null ,false) ;
       List<Advertisement> datas = (List<Advertisement>) apiResult.getData();
       if (!ListUtils.isEmpty(datas)){
           Collections.sort(datas,(o1, o2) -> {
               if (o1.getOrderNum()==null){
                   o1.setOrderNum(0);
               }
               if (o2.getOrderNum()==null){
                   o2.setOrderNum(0);
               }
               return o2.getOrderNum()-o1.getOrderNum();
           });
           return  datas.get(0);
       }
        return null;
    }

    @Override
    public Advertisement getSmallerOrderNum() {
        ApiResult apiResult = findAllAds(null,null ,false) ;
        List<Advertisement> datas = (List<Advertisement>) apiResult.getData();
        if (!ListUtils.isEmpty(datas)){
            Collections.sort(datas,(o1, o2) -> {
                if (o1.getOrderNum()==null){
                    o1.setOrderNum(0);
                }
                if (o2.getOrderNum()==null){
                    o2.setOrderNum(0);
                }
                return o1.getOrderNum()-o2.getOrderNum();
            });
            return  datas.get(0);
        }
        return  null ;
    }


    @Override
    public ApiResult setOneToBiggest(Long id) {
        try {
            Advertisement advertisement =findById(id);

            Advertisement adCompare = getBiggestOrderNum();

            if (adCompare.getOrderNum()>advertisement.getOrderNum()){
                advertisement.setOrderNum(adCompare.getOrderNum()+1);
                advertisement= r.saveAndFlush(advertisement);
            }
           return  new ApiResult(ResultMsg.SUCCESS,advertisement);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
            return  new ApiResult(e.getResultMsg(),null);
        }


    }
    @Override
    public ApiResult getById(Long id){
        if(id==null){
            return new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        Advertisement byId = null;
        try{
            byId = findById(id);
        }catch (SmartCommunityException e){
            return new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        if(byId==null){
            return new ApiResult(ResultMsg.NOT_FOUND,null);
        }
        return new ApiResult(ResultMsg.SUCCESS,byId);
    }
}
