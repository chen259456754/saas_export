package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.ContractProduct;
import cn.itcast.domain.cargo.ContractProductExample;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.utils.FileUploadUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(path = "/cargo/contractProduct")
public class ContractProductController extends BaseController {
    @DubboReference
    private ContractProductService contractProductService;
    @DubboReference
    private FactoryService factoryService;
    @Resource
    private FileUploadUtil fileUploadUtil;

    /**
     * 货物列表显示
     * 分析：
     * 1. 查询生产厂家
     * 2. 根据购销合同id，查询该购销合同下所有货物
     * 3. 存储购销合同id
     * 4. 返回
     */
    @RequestMapping(path = "/list")
    public String list(String contractId,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        //查询该货物的生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        //根据购销合同的id，查询该购销合同下所属的所有货物
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria cpExampleCriteria = contractProductExample.createCriteria();
        cpExampleCriteria.andContractIdEqualTo(contractId);
        PageInfo<ContractProduct> pageInfo = contractProductService.findByPage(contractProductExample, pageNum, pageSize);
        //向请求域中添加pageInfo和购销合同的id
        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-list";
    }

    /**
     * 2、货物添加或修改
     * 功能入口：project-list.jsp 点击保存
     */
    @RequestMapping(path = "/edit")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {
        //设置企业信息
        contractProduct.setCompanyId(getLoginCompanyId());
        contractProduct.setCompanyName(getLoginCompanyName());
        if (StringUtil.isEmpty(contractProduct.getId())) {
            //如果上传文件不为空，调用工具类处理文件上传
            if (productPhoto != null) {
                String fileUrl = "http://" + fileUploadUtil.upload(productPhoto);
                //将文件路径保存到数据库
                contractProduct.setProductImage(fileUrl);
            }
            contractProductService.save(contractProduct);
        } else {
            contractProductService.update(contractProduct);
        }
        //重定向到列表
        return "redirect:/cargo/contractProduct/list?contractId=" + contractProduct.getContractId();
    }

    /**
     * 3、进入修改页面
     * http://localhost:8080/cargo/contractProduct/toUpdate.do?id=4
     */
    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String id) {
        //根据货物id查询货物
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct", contractProduct);

        //查询厂家
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        return "cargo/product/product-update";
    }

    /**
     * 4、删除货物
     * 请求地址：http://localhost:8080/cargo/contractProduct/delete.do
     * 请求参数：
     * id                货物id
     * contractId        购销合同id
     */
    @RequestMapping(path = "/delete")
    public String delete(String contractId, String id) {
        //根据货物id删除货物
        contractProductService.delete(id);
        //重定向到列表
        return "redirect:/cargo/contractProduct/list?contractId=" + contractId;
    }

}
