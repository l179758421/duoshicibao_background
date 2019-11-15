package com.runer.cibao.util.machine;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/22
 * 随机类工具
 **/
@Component
public class RandomMachine {

    /**
     * 随机抽取一个对象
     * @param data
     * @return
     */
    public Object randomCatchOne(List data) throws Exception{

        if (data==null||data.isEmpty()){
            throw  new Exception("没有数据，数据为空");
        }
        int count =data.size() ;

        int randomObjectIndex =RandomUtils.nextInt(0,count) ;

        if (randomObjectIndex>=count){
           randomCatchOne(data);
        }

        return  data.get(randomObjectIndex) ;
    }


    public List randomCatchDatasExceptOne(List data ,int count ,Object exceptOne) throws Exception{

      if (data==null||data.isEmpty()){
          throw  new Exception("没有数据，数据为空");
      }

      //移除
      if (data.contains(exceptOne)){
          data.remove(exceptOne);
      }

      //移除以后空了，不允许；
      if (data.isEmpty()){
          throw   new Exception("数据池太小");
      }

       List res =  randomCathDatas(data,count) ;

        return  res ;

    }



    /**
     * 随机抽取固定个数的数据
     */
    public List randomCathDatas(List  data ,int count) throws  Exception{

        if (data==null||data.isEmpty()){
            throw  new Exception("没有数据，数据为空");
        }
        //选取的随机数量
        int totalRandomCount =count ;

        // 当数据量不足的情况下直接返回总数据；
        if (count>data.size()){
            return  data ;
        }

        /**
         * 数据量充足的情况下；
         */
        Set<Integer> indexs =new HashSet<>();

        //当选取的index小于总的获取数的情况下
        while (indexs.size()<totalRandomCount){
            int randomIndex =RandomUtils.nextInt(0,data.size());
            indexs.add(randomIndex);
        }

        //填充数据
        List<Object> results =new ArrayList<>() ;
        for (Integer index : indexs) {
            results.add(data.get(index)) ;
        }

        return  results  ;

    }








}
