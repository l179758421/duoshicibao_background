package com.runer.cibao.util;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/25
 **/
public class DownloadUtil {




    public static ResponseEntity<FileSystemResource> export(String  filePath) throws  Exception {
        File file =new File(filePath) ;
        if (!file.exists()){
            throw  new Exception("当前文件不存在");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        String headerValue = String.format("attachment; filename=\"%s\"",
                file.getName());
        headers.add("Content-Disposition", headerValue);
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }





    public static void downLoad(String filePath , HttpServletRequest request , HttpServletResponse response) throws Exception{


        if (!new File(filePath).exists()){
            throw  new Exception("当前文件不存在");
        }


        File downloadFile = new File(filePath);

        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(filePath);


        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            System.err.println("context getMimeType is null");
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            InputStream myStream = new FileInputStream(filePath);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
