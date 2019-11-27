package com.runer.cibao.service.impl;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.GoodTeaches;
import com.runer.cibao.domain.Read;
import com.runer.cibao.domain.repository.GoodTeachesRespository;
import com.runer.cibao.domain.repository.ReadRespository;
import com.runer.cibao.exception.ResultMsg;
import com.runer.cibao.service.GoodTeachesService;
import com.runer.cibao.service.ReadService;
import org.springframework.stereotype.Service;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/9
 **/

@Service
public class ReadServiceImpl extends BaseServiceImp<Read, ReadRespository> implements ReadService {

    @Override
    public ApiResult addRead(Long id,
                             String imgUrl,
                             String title, String author, String content) {
        Read read =new Read();
        if (id!=null){
            ApiResult readResult = findByIdWithApiResult(id);
            if (readResult.isFailed()){
                return  readResult ;
            }
            read = (Read) readResult.getData();
        }
        read.setImgUrl(imgUrl);
        read.setTitle(imgUrl);
        read.setAuthor(author);
        read.setContent(content);
        r.saveAndFlush(read) ;
        return new ApiResult(ResultMsg.SUCCESS,read);
    }
}
