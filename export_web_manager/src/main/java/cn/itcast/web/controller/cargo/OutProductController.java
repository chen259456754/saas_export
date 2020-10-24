package cn.itcast.web.controller.cargo;


import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.vo.ContractProductVo;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.DownloadUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping(path = "/cargo/contract")
public class OutProductController extends BaseController {
    @DubboReference
    private ContractProductService contractProductService;

    //1. 进入出货表打印页面
    @RequestMapping("/print")
    public String print() {
        return "cargo/print/contract-print";
    }

    //3. 导出出货表： 模板导出
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws Exception {
        //第一步：创建工作簿、创建工作表
        InputStream in =
                session.getServletContext().
                        getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        Workbook workbook = new XSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);

        // 第二步： 获取第一行
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        //指定第一行标题行的内容:2012-08  --->  2012年8月份出货表
        String value = inputDate.replace("-0","-").replace("-","年") + "月份出货表";
        cell.setCellValue(value);

        // 第三步： 获取第三行样式
        row = sheet.getRow(2);
        CellStyle[] cellStyles = new CellStyle[8];
        for (int i=0; i<cellStyles.length; i++){
            cellStyles[i] = row.getCell(i+1).getCellStyle();
        }

        // 第四步： 调用service查询、导出数据行
        List<ContractProductVo> list =
                contractProductService.findByShipTime(getLoginCompanyId(), inputDate);
        if (list != null && list.size() > 0){
            int index = 2;
            for (ContractProductVo cp : list) {
                row = sheet.createRow(index++);
                cell = row.createCell(1);
                cell.setCellValue(cp.getCustomName());
                cell.setCellStyle(cellStyles[0]);

                cell = row.createCell(2);
                cell.setCellValue(cp.getContractNo());
                cell.setCellStyle(cellStyles[1]);

                cell = row.createCell(3);
                cell.setCellValue(cp.getProductNo());
                cell.setCellStyle(cellStyles[2]);

                cell = row.createCell(4);
                cell.setCellValue(cp.getCnumber());
                cell.setCellStyle(cellStyles[3]);

                cell = row.createCell(5);
                cell.setCellValue(cp.getFactoryName());
                cell.setCellStyle(cellStyles[4]);

                cell = row.createCell(6);
                cell.setCellValue(cp.getDeliveryPeriod());
                cell.setCellStyle(cellStyles[5]);

                cell = row.createCell(7);
                cell.setCellValue(
                        new SimpleDateFormat("yyyy-MM-dd").format(cp.getShipTime()));
                cell.setCellStyle(cellStyles[6]);

                cell = row.createCell(8);
                cell.setCellValue(cp.getTradeTerms());
                cell.setCellStyle(cellStyles[7]);
            }
        }

        // 第五步：导出下载
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        new DownloadUtil().download(bos,response,"出货表.xlsx");
    }

    //大标题的样式
    public CellStyle bigTitle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        //加粗
        font.setBold(true);
        style.setFont(font);
        //横向居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //纵向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //丄细线
        style.setBorderTop(BorderStyle.THIN);
        //下细线
        style.setBorderBottom(BorderStyle.THIN);
        //左细线
        style.setBorderLeft(BorderStyle.THIN);
        //右细线
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    //文字样式
    public CellStyle text(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        //横向居左
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //丄细线
        style.setBorderTop(BorderStyle.THIN);
        //下细线
        style.setBorderBottom(BorderStyle.THIN);
        //左细线
        style.setBorderLeft(BorderStyle.THIN);
        //右细线
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

}
