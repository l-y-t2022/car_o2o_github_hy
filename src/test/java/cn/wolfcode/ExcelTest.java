package cn.wolfcode;

import cn.wolfcode.domain.Role;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {
    @Test
    public void readGoods() throws IOException {
        //创建Workbook，要根据哪个文件创建Workbook
        //这样才能调用其api从这个对象获取对应excel文件内容
        FileInputStream fis = new FileInputStream("C:\\Users\\fat\\Desktop\\04 项目一-杨龙\\04 项目一-杨龙\\day10 e店邦企业项目-POI\\课堂资料\\商品信息表.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        //获取第一个工作簿
        Sheet sheet = workbook.getSheetAt(0);
        //获取表的行数
        int num = sheet.getLastRowNum();//从0开始
        for (int i = 0; i <= num; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                if (cell.getCellType().equals(CellType.STRING)) {
                    System.out.print(cell.getStringCellValue()+" ");
                } else if(cell.getCellType().equals(CellType.NUMERIC)){
                    System.out.print(cell.getNumericCellValue()+" ");
                } else if(cell.getCellType().equals(CellType.BOOLEAN)){
                    System.out.print(cell.getBooleanCellValue()+" ");
                } else if(cell.getCellType().equals(CellType.FORMULA)){
                    System.out.print(cell.getCellFormula()+" ");
                } else {
                    System.out.print("");
                }
            }
            System.out.println("");
        }

        //关闭资源
        fis.close();
        workbook.close();
    }
    @Test
    public void read07() throws IOException {
        //创建Workbook，要根据哪个文件创建Workbook
        //这样才能调用其api从这个对象获取对应excel文件内容
        FileInputStream fis = new FileInputStream("d:/write07.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);

        //获取第一个工作簿
        Sheet sheet = workbook.getSheetAt(0);
        String s1 = sheet.getRow(1).getCell(0).getStringCellValue();//获取第2行第1个单元格的内容
        System.out.println("s1 = " + s1);

        fis.close();
        workbook.close();
    }


    @Test
    public void read03() throws IOException {
        //创建Workbook，要根据哪个文件创建Workbook
        //这样才能调用其api从这个对象获取对应excel文件内容
        FileInputStream fis = new FileInputStream("d:/write03.xls");
        Workbook workbook = new HSSFWorkbook(fis);

        //获取第一个工作簿
        Sheet sheet = workbook.getSheetAt(0);
        String s1 = sheet.getRow(0).getCell(0).getStringCellValue();//获取第一行第一个单元格的内容
        System.out.println("s1 = " + s1);

        fis.close();
        workbook.close();
    }

    //excel7能写的内容多，但是速度慢
    //excel3能写的内容有限，但是速度快

    @Test
    public void testExcel07Super() throws IOException {
        //创建excel文件
        Workbook workbook = new SXSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet();

        long start = System.currentTimeMillis();
        //一次写入多行,超过65535行写太多行会报错
        for (int i = 0; i < 65536; i++) {//创建1w行
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {//每行创建10个单元格
                row.createCell(j).setCellValue(j);

            }
        }
        //将内存中写好的excel文件内容写在磁盘中
        FileOutputStream fileOutputStream = new FileOutputStream("d:/write07Super.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

        //清除临时文件
        ((SXSSFWorkbook)workbook).dispose();
        long end = System.currentTimeMillis();
        System.out.println("花费时间："+(end-start));

    }
    @Test
    public void testExcel07Big() throws IOException {
        //创建excel文件

        Workbook workbook = new XSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet();

        long start = System.currentTimeMillis();
        //一次写入多行,超过65535行写太多行会报错
        for (int i = 0; i < 65536; i++) {//创建1w行
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {//每行创建10个单元格
                row.createCell(j).setCellValue(j);

            }
        }

        //将内存中写好的excel文件内容写在磁盘中
        FileOutputStream fileOutputStream = new FileOutputStream("d:/write07Big.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
        long end = System.currentTimeMillis();
        System.out.println("花费时间："+(end-start));
    }
    @Test
    public void testExcel03Big() throws IOException {
        //创建excel文件

        Workbook workbook = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet();

        long start = System.currentTimeMillis();
        //一次写入多行,超过65535行写太多行会报错
        for (int i = 0; i < 65536; i++) {//创建1w行
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {//每行创建10个单元格
                row.createCell(j).setCellValue(j);

            }
        }
        //将内存中写好的excel文件内容写在磁盘中
        FileOutputStream fileOutputStream = new FileOutputStream("d:/write03big.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
        long end = System.currentTimeMillis();
        System.out.println("花费时间："+(end-start));
    }

    @Test
    public void testExcel07() throws IOException {
        //创建excel文件
        Workbook workbook = new XSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet("疫苗接种情况");

        //第一行从0开始，在工作簿中创建行
        Row row = sheet.createRow(0);
        Cell cell00 = row.createCell(0);
        cell00.setCellValue("今日人数");

        //在行中创建单元格，这行的第二个单元格
        Cell cell01 = row.createCell(1);
        cell01.setCellValue("666");

        //创建第二行
        Row row1 = sheet.createRow(1);
        //获取第二行的第一个单元格
        Cell cell10 = row1.createCell(0);
        //给单元格设置值
        cell10.setCellValue("统计时间");

        //在行中创建单元个，这行的第二个单元格
        Cell cell11 = row1.createCell(1);
        cell11.setCellValue(new DateTime().toString("yyyy-MM-dd"));

        //将内存中写好的excel文件内容写在磁盘中
        FileOutputStream fileOutputStream = new FileOutputStream("d:/write07.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
    @Test
    public void testExcel03() throws IOException {
        //创建excel文件

        Workbook workbook = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet();

        //第一行从0开始，在工作簿中创建行
        Row row = sheet.createRow(0);
        Cell cell00 = row.createCell(0);
        cell00.setCellValue("今日人数");

        //在行中创建单元个，这行的第二个单元格
        Cell cell01 = row.createCell(1);
        cell01.setCellValue("666");

        //
        Row row1 = sheet.createRow(1);
        Cell cell10 = row1.createCell(0);
        cell10.setCellValue("统计时间");

        //在行中创建单元个，这行的第二个单元格
        Cell cell11 = row1.createCell(1);
        cell11.setCellValue(new DateTime().toString("yyyy-MM-dd"));

        //将内存中写好的excel文件内容写在磁盘中
        FileOutputStream fileOutputStream = new FileOutputStream("d:/write03.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
