package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductVo;
import cn.itcast.vo.ExportResult;
import cn.itcast.vo.ExportVo;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/cargo/export")
public class ExportController extends BaseController {
    @DubboReference(timeout = 10000000)
    private ContractService contractService;
    @DubboReference(timeout = 10000000)
    private ExportService exportService;
    @DubboReference(timeout = 10000000)
    private ExportProductService exportProductService;


    /**
     * 合同管理 列表： 只显示购销合同状态为1的记录。
     */
    @RequestMapping(path = "/contractList")
    public String contractList(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize) {
        ContractExample contractExample = new ContractExample();
        contractExample.setOrderByClause("create_time desc");
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        //查询条件：状态为1 (已上报)
        criteria.andStateEqualTo(1);
        PageInfo<Contract> pageInfo = contractService.findByPage(contractExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/export/export-contractList";
    }

    /**
     * 出口报运单列表
     */
    @RequestMapping(path = "/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        String companyId = getLoginCompanyId();
        ExportExample exportExample = new ExportExample();
        exportExample.setOrderByClause("create_time desc");
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo<Export> pageInfo = exportService.findByPage(exportExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "cargo/export/export-list";
    }

    /**
     * 合同管理点击报运
     *
     * @param id 保存在合同管理页面中用户选择的多个购销合同，用逗号隔开
     * @return
     */
    @RequestMapping(path = "/toExport")
    public String toExport(String id) {
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }

    /**
     * 生产出口报运单，以及修改报运单
     *
     * @param export
     * @return
     */
    @RequestMapping(path = "/edit")
    public String edit(Export export) {
        export.setCompanyId(getLoginCompanyId());
        export.setCompanyName(getLoginCompanyName());
        if (StringUtil.isEmpty(export.getId())) {
            exportService.save(export);
        } else {
            exportService.update(export);
        }
        return "redirect:/cargo/export/list";
    }

    /**
     * 进入编辑页面
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String id) {
        //根据id查询报运单
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        //查询此报运单下的所有商品
        ExportProductExample epExample = new ExportProductExample();
        epExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(epExample);
        request.setAttribute("eps", eps);
        return "cargo/export/export-update";
    }

    /**
     * 出口报运列表，点击取消
     */
    @RequestMapping(path = "/cancel")
    public String cancel(String id) {
        Export export = exportService.findById(id);
        export.setState(0);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 出口报运列表，点击提交
     */
    @RequestMapping(path = "/submit")
    public String submit(String id) {
        Export export = exportService.findById(id);
        export.setState(1);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 电子报运
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/exportE")
    public String exportE(String id) {
        //1.根据报运单id查询报运单对象
        Export export = exportService.findById(id);

        //2.根据报运单id查询报运商品列表
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> eps = exportProductService.findAll(example);

        //3.构造电子报运的VO对象，并赋值
        ExportVo vo = new ExportVo();
        BeanUtils.copyProperties(export, vo);
        vo.setExportId(export.getId());
        List<ExportProductVo> products = new ArrayList<>();
        //构造报运商品数据
        if (eps != null && eps.size() > 0) {
            for (ExportProduct ep : eps) {
                ExportProductVo epv = new ExportProductVo();
                BeanUtils.copyProperties(ep, epv);
                epv.setExportProductId(ep.getId());
                products.add(epv);
            }
        }
        vo.setProducts(products);

        //4.电子报运
        WebClient client = WebClient.create("http://localhost:9001/ws/export/user");
        client.post(vo);
        //5.查询报运结果
        client = WebClient.create("http://localhost:9001/ws/export/user/" + id);
        ExportResult result = client.get(ExportResult.class);

        //6.调用service完成报运结果的入库
        exportService.updateExport(result);
        return "redirect:/cargo/export/list.do";
    }


}
