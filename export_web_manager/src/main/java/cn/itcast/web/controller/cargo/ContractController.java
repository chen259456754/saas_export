package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/cargo/contract")
public class ContractController extends BaseController {
    //注入购销合同的服务接口
    @DubboReference
    private ContractService contractService;

    @RequestMapping(path = "/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        //构造查询条件
        ContractExample example = new ContractExample();
        //根据create_time进行降序
        example.setOrderByClause("create_time desc");
        //查询条件对象
        ContractExample.Criteria criteria = example.createCriteria();
        //查询条件：企业id
        criteria.andCompanyIdEqualTo(getLoginCompanyId());
        //调用service查询
        PageInfo<Contract> pageInfo = contractService.findByPage(example, pageNum, pageSize);
        //返回
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("cargo/contract/contract-list");
        return mv;
    }

    /**
     * 进入购销合同的添加页面
     *
     * @return
     */
    @RequestMapping(path = "/toAdd")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    /**
     * 添加或修改
     *
     * @param contract
     * @return
     */
    @RequestMapping(path = "/edit")
    public String edit(Contract contract) {
        contract.setCompanyId(getLoginCompanyId());
        contract.setCompanyName(getLoginCompanyName());
        if (StringUtil.isEmpty(contract.getId())) {
            contractService.save(contract);
        } else {
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list";
    }

    /**
     * 进入修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-update";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/delete")
    public String delete(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list";
    }

    /**
     * 查看、提交、取消
     * http://localhost:8080/cargo/contract/toView.do?id=?
     * http://localhost:8080/cargo/contract/submit.do?id=?
     * http://localhost:8080/cargo/contract/cancel.do?id=?
     */
    @RequestMapping(path = "/toView")
    public String toView(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-view";
    }

    /**
     * 提交：修改合同的状态为1。0为草稿。
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/submit")
    public String submit(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        //修改状态
        contract.setState(1);
        //动态sql，只更新有值的字段，其他字段值不变。
        contractService.update(contract);
        return "redirect:/cargo/contract/list";
    }

    /**
     * 同提交
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/cancel")
    public String cancel(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        //修改状态
        contract.setState(0);
        //动态sql，只更新有值的字段，其他字段值不变。
        contractService.update(contract);
        return "redirect:/cargo/contract/list";
    }

}
