package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.GoodTeaches;
import com.runer.cibao.domain.repository.GoodTeachesRespository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.GoodTeachesService;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/9
 **/

@Service
public class GoodTeachesServiceImpl extends BaseServiceImp<GoodTeaches, GoodTeachesRespository> implements GoodTeachesService {

    @Override
    public ApiResult addGoodTeaches(Long id,
                                    String title ,
                                    String url,
                                    String imgUrl, String teacherName, String teacherDes, long wathNum, double score, int isBought) {
        GoodTeaches goodTeaches =new GoodTeaches();
        if (id!=null){
            ApiResult goodTeachesResult = findByIdWithApiResult(id);
            if (goodTeachesResult.isFailed()){
                return  goodTeachesResult ;
            }
            goodTeaches = (GoodTeaches) goodTeachesResult.getData();
        }

        goodTeaches.setImgUrl(imgUrl);
        goodTeaches.setTeacherDes(teacherDes);
        goodTeaches.setTeacherName(teacherName);
        goodTeaches.setWathNum(wathNum);
        goodTeaches.setScore(score);
        goodTeaches.setIsBought(isBought);
        goodTeaches.setTitle(title);
        goodTeaches.setUrl(url);

        r.saveAndFlush(goodTeaches) ;
        return new ApiResult(ResultMsg.SUCCESS,goodTeaches);
    }
}
