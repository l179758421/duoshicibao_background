package com.runer.cibao.config;

import com.runer.cibao.domain.Power;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.thymeleaf.util.ListUtils;

import java.io.InputStream;
import java.util.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/5
 **/
public class Menu {

    /**
     * @param powers
     * @return
     */
    public static List<Power> parsePower(List<Power> powers){
        List<Power>resultPowers =new ArrayList<>() ;
        HashMap<Long, Power> praentPower =new HashMap<>() ;
        powers.forEach(power -> {
            if (power.getParentId()==null){
                praentPower.put(power.getId(),power);
            }
            String dataPath ="{url:'"+power.getPowerUrl()+"',title:'"+power.getPowerName()+"',icon:'"+power.getIcon()+"',id:'"+power.getId()+"'}" ;
            power.setDataPath(dataPath);
        });
        powers.forEach(power -> {
           if (power.getParentId()!=null){
               praentPower.get(power.getParentId()).getPowerList().add(power);
           }
        });
        praentPower.forEach((aLong, power) -> {
            if (!ListUtils.isEmpty(praentPower.get(aLong).getPowerList())){
                power.setHasChild(true);
            }
            resultPowers.add(power);
        });
        /*排一下序列！！*/
        Collections.sort(resultPowers,(o1, o2) -> (int) (o1.getId()-o2.getId()));
        return  resultPowers ;
    }
    /**
     *
     * @return
     */
    public static List<Power> getPowers(){

        List<Power> powersItem =new ArrayList<>() ;
        SAXReader reader = new SAXReader();
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            ClassPathResource resource = new ClassPathResource("powers.xml");
            //使用这个方法！防止发布jar不能找到资源！
            InputStream inputStream = resource.getInputStream();
            Document document = reader.read(inputStream);
            // 通过document对象获取根节点
            Element powers = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = powers.elementIterator();
            // 遍历迭代器，获取根节点
            while (it.hasNext()) {
                Power powerItem =new Power() ;
                Element power = (Element) it.next();
                // 获取b属性名以及 属性值
                List<Attribute> bookAttrs = power.attributes();
                for (Attribute attr : bookAttrs) {
                }
                Iterator itt = power.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    switch (bookChild.getName()){
                        case "id":
                            powerItem.setId(Long.valueOf(bookChild.getStringValue()));
                            break;
                        case "praentId":
                            if (!StringUtils.isEmpty(bookChild.getStringValue())) {
                                powerItem.setParentId(Long.valueOf(bookChild.getStringValue()));
                            }
                            break;
                        case "name":
                            powerItem.setPowerName(bookChild.getStringValue());
                            break;
                        case "path":
                            powerItem.setPowerUrl(bookChild.getStringValue());
                            break;
                        case "icon":
                            powerItem.setIcon(HtmlUtils.htmlEscape(bookChild.getStringValue()));
                        break;
                    }
                }
                powersItem.add(powerItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  powersItem ;
    }




}
