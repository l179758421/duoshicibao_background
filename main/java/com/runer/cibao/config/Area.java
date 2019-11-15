package com.runer.cibao.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runer.cibao.domain.City;
import com.runer.cibao.domain.Province;
import com.runer.cibao.domain.repository.AreaRepository;
import com.runer.cibao.domain.repository.CityRepository;
import com.runer.cibao.domain.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;
import sun.misc.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/5
 **/
@Component
public class Area {


    @Value("classpath:area/area.json")
    Resource areas;

    @Value("classpath:area/city.json")
    Resource cities ;

    @Value("classpath:area/province.json")
    Resource provices;




    @Autowired
    AreaRepository areaRepository ;

    @Autowired
    ProvinceRepository provinceRepository ;

    @Autowired
    CityRepository cityRepository ;




    public void init() throws IOException {



        /**
         * 获得省市列表
         */
        List<Province> provinceList =new ArrayList<>();
        if (ListUtils.isEmpty(provinceRepository.findAll())){
            String proviceJson =new String(IOUtils.readFully(provices.getInputStream(),-1,true));
            List<Province> finalProvinceList = provinceList;
            JSON.parseArray(proviceJson).forEach(o -> {
                JSONObject object = (JSONObject) o;
                Province province =new Province() ;
                province.setName(object.getString("name"));
                province.setId(Long.valueOf(object.getString("id")));
                finalProvinceList.add(province);
            });
            provinceList =finalProvinceList ;
            provinceRepository.saveAll(provinceList);
        }else{
            provinceList=provinceRepository.findAll();

           // System.err.println(new ObjectMapper().writeValueAsString(provinceList.get(3).getCityList()));


        }


         /**
         * 获得城市列表
         */
        List<City> cityList =new ArrayList<>() ;
       if (ListUtils.isEmpty(cityRepository.findAll())){
           String cityJson =new String(IOUtils.readFully(cities.getInputStream(),-1,true));
           JSONObject jsonObject =JSON.parseObject(cityJson);
           List<City> finalCityList = cityList;
           provinceList.forEach(province -> {
               if (jsonObject.containsKey(String.valueOf(province.getId()))) {
                   jsonObject.getJSONArray(String.valueOf(province.getId())).forEach(o -> {
                       JSONObject object = (JSONObject) o;
                       City city = new City();
                       city.setId(Long.valueOf(object.getString("id")));
                       city.setProvinceEntity(province);
                       city.setName(object.getString("name"));
                       city.setProvince(object.getString("province"));
                       finalCityList.add(city);
                   });
               }
           });
           cityList =finalCityList ;
           cityRepository.saveAll(cityList);
       }else{
            cityList =cityRepository.findAll() ;
       }

        /**
         * 获得地区列表
         */
       if (ListUtils.isEmpty(areaRepository.findAll())){
           String areasJson = new String(IOUtils.readFully(areas.getInputStream(), -1,true));
           List<com.runer.cibao.domain.Area > areaList =new ArrayList<>() ;
           JSONObject jsonObject1= JSONObject.parseObject(areasJson);
           cityList.forEach(city -> {
               if (jsonObject1.containsKey(String.valueOf(city.getId()))){
                   jsonObject1.getJSONArray(String.valueOf(city.getId())).forEach(o -> {
                       JSONObject jsonObject2 = (JSONObject) o;
                       com.runer.cibao.domain.Area area =new com.runer.cibao.domain.Area();
                       area.setCityEntity(city);
                       area.setId(Long.valueOf(jsonObject2.getString("id")));
                       area.setName(jsonObject2.getString("name"));
                       area.setCity(jsonObject2.getString("city"));
                       areaList.add(area);
                   });
               }
           });
           areaRepository.saveAll(areaList);

       }

    }

}
