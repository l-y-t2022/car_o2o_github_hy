package cn.wolfcode;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest2 {
    //按行按列读取内容
    @Test
    public void readAuto() throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\fat\\Desktop\\04 项目一-杨龙\\04 项目一-杨龙\\day10 e店邦企业项目-POI\\课堂资料\\商品信息表.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        //获取第一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()返回最后一行的行数(从1开始)
            //获取行
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {//row.getLastCellNum()获取最后单元格的下标(从0开始)
                //获取单元格
                Cell cell = row.getCell(j);
                //获取单元格的值
                if (cell.getCellType().equals(CellType.NUMERIC)) {
                    System.out.print(cell.getNumericCellValue()+" ");
                } else if (cell.getCellType().equals(CellType.STRING)) {
                    System.out.print(cell.getStringCellValue()+" ");
                } else if (cell.getCellType().equals(CellType.BOOLEAN)) {
                    System.out.print(cell.getBooleanCellValue()+" ");
                } else if (cell.getCellType().equals(CellType.FORMULA)) {
                    System.out.print(cell.getCellFormula()+" ");
                } else {
                    System.out.print(" ");
                }
            }
            //每行换行
            System.out.println();
        }
        //关闭资源
        fis.close();
        workbook.close();
    }
    //读取不同类型的数据
    @Test
    public void readDifParam() throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\fat\\Desktop\\04 项目一-杨龙\\04 项目一-杨龙\\day10 e店邦企业项目-POI\\课堂资料\\商品信息表.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        //获取第二行的数据
        Row row = sheet.getRow(1);
        System.out.println(row.getCell(0).getStringCellValue());//获取文本信息
        System.out.println(row.getCell(1).getNumericCellValue());//获取数值类型
        System.out.println(row.getCell(2).getDateCellValue());//获取时间类型
        System.out.println(row.getCell(3).getBooleanCellValue());//获取布尔类型的内容
        System.out.println(row.getCell(4).getStringCellValue());//获取空白内容
        System.out.println(row.getCell(5).getBooleanCellValue());//获取布尔类型的内容

        //关闭资源
        fis.close();
        workbook.close();

    }
    //读取xlsx
    @Test
    public void read07() throws IOException {
        FileInputStream fis = new FileInputStream("d:/test07.xlsx");
        //创建Excel文件，并读取输入流
        Workbook workbook = new XSSFWorkbook(fis);
        //获取sheet
        Sheet sheet = workbook.getSheetAt(0);//获取第一个sheet

        //读取第二行第二列
        String value = sheet.getRow(1).getCell(1).getStringCellValue();
        System.out.println(value);

        //关闭操作
        fis.close();
        workbook.close();

    }


    //读文件xls
    @Test
    public void read03() throws IOException {
        FileInputStream fis = new FileInputStream("d:/test03.xls");
        //创建Excel文件，并读取输入流
        Workbook workbook = new HSSFWorkbook(fis);
        //获取sheet
        Sheet sheet = workbook.getSheetAt(0);//获取第一个sheet

        //读取第二行第二列
        String value = sheet.getRow(1).getCell(1).getStringCellValue();
        System.out.println(value);

        //关闭操作
        fis.close();
        workbook.close();

    }

    //大文件写入,只能写eccel2007xlsx
    @Test
    public void writeBig() throws IOException {
        //创建excel
        Workbook workbook = new SXSSFWorkbook();
        //创建工作簿
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < 1000000 ; i++) {
            //创建行
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                //创建单元格
                row.createCell(j).setCellValue(j);
            }
        }

        //将文件写到磁盘
        FileOutputStream fos = new FileOutputStream("d:/test07big.xlsx");
        workbook.write(fos);
        //关闭资源
        fos.close();
        workbook.close();


        //清除缓存文件
        ((SXSSFWorkbook)workbook).dispose();
    }

    //写入
    @Test
    public void write07() throws Exception{
        //创建Excel文件
        Workbook workbook = new XSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet("读取excel07");
        //创建行
        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("序号");
        row1.createCell(1).setCellValue("人员");
        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue(1);
        row2.createCell(1).setCellValue("张素祯");

        FileOutputStream fos = new FileOutputStream("d:/test07.xlsx");
        workbook.write(fos);

        fos.close();
        workbook.close();
    }
    @Test
    public void write03() throws Exception{
        //创建Excel文件
        Workbook workbook = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet("读取excel03");
        //创建行
        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("序号");
        row1.createCell(1).setCellValue("人员");
        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue(1);
        row2.createCell(1).setCellValue("罗怡曼");

        FileOutputStream fos = new FileOutputStream("d:/test03.xls");
        workbook.write(fos);

        fos.close();
        workbook.close();
    }
}
