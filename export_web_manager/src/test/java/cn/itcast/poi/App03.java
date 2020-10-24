package cn.itcast.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 写
 * |-- Workbook 接口
 * |-- HSSFWorkbook  操作excel03
 * |-- XSSFWorkbook  操作excel07
 */
public class App03 {
    @Test
    public void write() throws Exception {
        //创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建一个工作表
        Sheet sheet = workbook.createSheet();
        //创建行：第一行
        Row row = sheet.createRow(0);
        //创建单元格：第一行的第一列
        Cell cell = row.createCell(0);
        //设置内容
        cell.setCellValue("第一行，第一列");
        //导出
        workbook.write(new FileOutputStream("C:\\zone\\test.xls"));
        workbook.close();
    }

    @Test
    public void read() throws Exception {
        //1. 根据文件流，创建工作簿
        Workbook workbook = new HSSFWorkbook(new FileInputStream("c:\\zone\\test.xls"));
        //2. 获取工作簿
        Sheet sheet = workbook.getSheetAt(0);
        //3. 获取第一行
        Row row = sheet.getRow(0);
        //4. 获取第一行第一列
        Cell cell = row.getCell(0);
        //5. 获取内容
        System.out.println("第一行第一列：" + cell.getStringCellValue());
        System.out.println("获取总行数："+sheet.getPhysicalNumberOfRows());
        System.out.println("获取第一行总列数：" + row.getPhysicalNumberOfCells());
    }
}
