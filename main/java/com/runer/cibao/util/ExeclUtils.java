package com.runer.cibao.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

/**
 * Excel导出工具类
 */
public class ExeclUtils {

    public static void main( String[] args ) {
        String[] data = {"73982", "1", "NH", "1", "2018122510", "2", "0", "0", "0", "12233", "0", "楼层组件优化", "0"};

        Date startTime = new Date();
        MemoryUsage startMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();

        // 创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("sheet1");

        //设置数据
        for (int row = 0; row < 50000; row++) {
            XSSFRow sheetRow = sheet.createRow(row);
            for (int column = 0; column < data.length; column++) {
                sheetRow.createCell(column).setCellValue(data[column]);
            }
        }

        //写入文件
        try {
            workbook.write(new FileOutputStream(new File("D://excel.xlsx")));
            workbook.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        Date endTime = new Date();
        MemoryUsage endMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        System.out.println("Cost time(ms): " + (endTime.getTime() - startTime.getTime()));
        System.out.println("Cost memory(): " + (endMemory.getUsed() - startMemory.getUsed()));

        System.out.println( "Hello World!" );
    }
}