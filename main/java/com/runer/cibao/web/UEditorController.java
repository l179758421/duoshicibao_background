package com.runer.cibao.web;

import com.baidu.ueditor.ActionEnter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author szhua
 * 2018-05-15
 * 10:42
 * @Descriptionssmartcommunity== UEditorController
 **/

@RestController
public class UEditorController {

    @Value("${web.upload-path}")
    private String basePath ;

    @RequestMapping(value="/plugins/umeditor/config")
    @ResponseBody
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            try{
                JSONObject jsonObject =new JSONObject(exec);
                if (jsonObject.has("basePath")){
                    jsonObject.put("basePath",basePath);
                }
                exec =jsonObject.toString();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
