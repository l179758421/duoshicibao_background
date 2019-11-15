package com.runer.cibao.util.machine;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/8/4
 **/
@Component
public class IdsMachine {


    public String toIds(List<Long> ids){
        if (ids==null||ids.isEmpty()){
            return  "" ;
        }
        StringBuilder builder =new StringBuilder() ;

        for (Long id : ids) {
            builder.append(id).append(",") ;
        }

        return  builder.toString() ;

    }



    public String parseIds(String ids ){
        try{
            StringBuffer stringBuffer =new StringBuffer() ;
            List<String> numbers = Arrays.asList(ids.split(","));
            for (String number : numbers) {
                if (! NumberUtils.isCreatable(number)){
                    continue;
                }
                stringBuffer.append(",") ;
                stringBuffer.append(number) ;
            }
            return  stringBuffer.toString() ;
        }catch (Exception e){
            e.printStackTrace();
            return "" ;
        }
    }

    public String deparseIdsToNormal(String ids){
        List<Long > idsList = deparseIds(ids);
        StringBuffer stringBuffer =new StringBuffer() ;
        idsList.forEach(number -> {
            stringBuffer.append(number+",") ;
        });
        return  stringBuffer.toString() ;
    }


    /**
     * 反向分析；
     * @param ids
     * @return
     */
    public List<Long > deparseIds(String ids ){
        List<Long >  numbers =new ArrayList<>() ;
        if (StringUtils.isEmpty(ids)){
            return   numbers ;
        }
        if (ids.length()==1&&!NumberUtils.isCreatable(ids)){
            return  numbers ;
        }

        if (ids.startsWith(",")){
            ids =ids.substring(1,ids.length());
        }
        try{
            for (String str : ids.split(",")) {
                if (! NumberUtils.isCreatable(str)){
                    continue;
                }
                numbers.add(Long.parseLong(str));
            }
        }catch (Exception e){e.printStackTrace();}

        return  numbers ;
    }


    public  void removeDuplicate(List list) {

        if (list==null){
            list =new ArrayList() ;
            return;
        }
        LinkedHashSet set = new LinkedHashSet(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }





}
