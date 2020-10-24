package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.ExtCproductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/cargo/extCproduct")
public class ExtCproductController extends BaseController {
    @DubboReference
    private ExtCproductService extCproductService;
    @DubboReference
    private FactoryService factoryService;

    @RequestMapping(path = "/list")
    public String list(String contractId, String contractProductId,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        //查询附件的生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria2 = factoryExample.createCriteria();
        //查找所有的附件厂家
        criteria2.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        //查询当前货物下的所有附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo<ExtCproduct> pageInfo = extCproductService.findByPage(extCproductExample, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);

        //设置页面的基本参数
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);

        return "cargo/extc/extc-list";
    }

    @RequestMapping(path = "/edit")
    public String edit(ExtCproduct extCproduct) {
        if (StringUtil.isEmpty(extCproduct.getId())) {
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list?contractId=" + extCproduct.getContractId()
                + "&contractProductId=" + extCproduct.getContractProductId();
    }

    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String contractId, String contractProductId, String id) {
        //查询附件
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct", extCproduct);

        //查询生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        FactoryExample.Criteria factoryList = factoryExampleCriteria.andCtypeEqualTo("附件");
        request.setAttribute("factoryList", factoryList);

        //保存参数
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);

        return "cargo/extc/extc-update";
    }

    @RequestMapping(path = "/delete")
    public String delete(String id, String contractId, String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list?contractId=" + contractId
                + "&contractProductId=" + contractProductId;
    }
}
