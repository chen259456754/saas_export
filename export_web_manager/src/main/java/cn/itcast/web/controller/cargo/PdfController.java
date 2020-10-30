package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportProduct;
import cn.itcast.domain.cargo.ExportProductExample;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.BeanMapUtils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping(path = "/cargo/export")
public class PdfController extends BaseController {

    @Resource
    private DataSource dataSource;
    @DubboReference
    private ExportService exportService;
    @DubboReference
    private ExportProductService exportProductService;

    /**
     * 导出PDF（1） HelloWorld  + 中文字体
     */
    @RequestMapping(path = "/exportPdf1")
    @ResponseBody
    public void exportPdf1() throws Exception {
        //1. 获取jasper文件流
        InputStream in = session.getServletContext().getResourceAsStream("/jasper/test01.jasper");
        //2. 创建JasperPrint对象，用于往模板中填充数据
        //参数1：jasper文件流
        //参数2：通过map方式往模板中填充数据
        //参数3：通过数据源的方式往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, new HashMap<>(), new JREmptyDataSource());
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }

    /**
     * 导出PDF（2） 往模板填充数据 A 通过map填充
     * 注意：map的key对应的是模板设置中的Parameter参数名称
     */
    @RequestMapping(path = "/exportPdf2")
    @ResponseBody
    public void exportPdf2() throws Exception {
        //1. 获取jasper文件流
        InputStream in = session.getServletContext().getResourceAsStream("/jasper/test02.jasper");
        //构造map集合
        Map<String, Object> map = new HashMap<>();
        map.put("username", "球球");
        map.put("email", "ballball@export.com");
        map.put("companyName", "字节跳动");
        map.put("deptName", "研发部");

        //2. 创建JasperPrint对象，用于往模板中填充数据
        //参数1：jasper文件流
        //参数2：通过map方式往模板中填充数据
        //参数3：通过数据源的方式往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, new JREmptyDataSource());
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }

    /**
     * 导出PDF（3） 往模板填充数据 B 数据源填充（1）jdbc数据源
     * 注意：map的key对应的是模板设置中的Parameter参数名称
     */
    @RequestMapping(path = "/exportPdf3")
    @ResponseBody
    public void exportPdf3() throws Exception {
        //1. 获取jasper文件流
        InputStream in = session.getServletContext().getResourceAsStream("/jasper/test03.jasper");

        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource.getConnection());
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }

    /**
     * 导出PDF（3） 往模板填充数据 B 数据源填充（1）jdbc数据源
     * 注意：map的key对应的是模板设置中的Parameter参数名称
     */
    @RequestMapping(path = "/exportPdf4")
    @ResponseBody
    public void exportPdf4() throws Exception {
        //1. 获取jasper文件流
        InputStream in = session.getServletContext().getResourceAsStream("/jasper/test04.jasper");

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //构造map集合
            Map<String, Object> map = new HashMap<>();
            map.put("userName", "球球" + i);
            map.put("email", "ballball@export.com");
            map.put("companyName", "字节跳动");
            map.put("deptName", "研发部");
            // 添加到集合
            list.add(map);
        }
        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource);
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        in.close();
    }

    /**
     * 导出PDF（5） 分组报表，根据企业名称自动进行分组
     */
    @RequestMapping(path = "/exportPdf5")
    @ResponseBody
    public void exportPdf5() throws Exception {
        //1. 获取jasper文件流
        InputStream in = session.getServletContext().getResourceAsStream("/jasper/test05.jasper");

        List<Map<String, Object>> list = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 5; i++) {
                //构造map集合
                Map<String, Object> map = new HashMap<>();
                map.put("userName", "球球" + i);
                map.put("email", "ballball@export.com");
                map.put("companyName", "字节跳动" + j);
                map.put("deptName", "研发部");
                // 添加到集合
                list.add(map);
            }
        }
        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource);
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        in.close();
    }

    /**
     * 导出PDF（6） 图形报表
     */
    @RequestMapping(path = "/exportPdf6")
    @ResponseBody
    public void exportPdf6() throws Exception {
        //1. 获取jasper文件流
        InputStream in = session.getServletContext().getResourceAsStream("/jasper/test06.jasper");

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //构造map集合
            Map<String, Object> map = new HashMap<>();
            // 注意：title、value对应的是模板中的Field，且类型也要对应
            map.put("title", "球球" + i);
            map.put("value", new Random().nextInt(100));
            // 添加到集合
            list.add(map);
        }
        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource);
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        in.close();
    }

    /**
     * 导出PDF, 导出报运详情
     */
    @RequestMapping("/exportPdf")
    @ResponseBody  // 不进行跳转
    public void exportPdf(String id) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/export.jasper");

        //A. 根据包运单id，查询获取报运单对象
        Export export = exportService.findById(id);
        // 对象转换为map
        Map<String, Object> map = BeanMapUtils.beanToMap(export);

        //B. 根据报运单id，查询商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        // 注意：集合中对象的属性，要于Field中的字段名称一致
        List<ExportProduct> list = exportProductService.findAll(example);

        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //2. 创建JasperPrint对象，用于往模板中填充数据
        // 参数2：通过map封装报运单，填充到模板
        // 参数3：通过集合封装商品，填充到模板
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, dataSource);
        //3. 导出、下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition","attachment;fileName=export.pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        in.close();
    }

}
