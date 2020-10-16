package cn.itcast.web.controller.company;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import cn.itcast.web.controller.system.BaseController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/company")
public class CompanyController extends BaseController {

    @Resource
    CompanyService companyService;

    /**
     * 列表查询
     * 请求路径：http://localhost:8080/company/list
     * 响应路径：/WEB-INF/pages/company/company-list.jsp
     */
    @RequestMapping(path = "/list", name = "企业列表")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {

        PageInfo<Company> pageInfo = companyService.findByPage(pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "company/company-list";
    }

    /**
     * 点击新建，进入添加页面
     * <p>
     * 功能入口：在company-list.jsp点击新建
     * 请求地址：http://localhost:8080/company/toAdd
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/company/company-add.jsp
     */
    @RequestMapping(path = "/toAdd")
    public String toAdd() {
        return "company/company-add";
    }


    @RequestMapping(path = "/edit")
    public String edit(Company company) {
        if (StringUtil.isEmpty(company.getId())) {
            companyService.save(company);
        } else {
            companyService.update(company);
        }
        return "redirect:/company/list";
    }

    @RequestMapping(path = "/delete")
    public String delete(String id) {
        companyService.delete(id);
        return "redirect:/company/list";
    }

    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String id, ModelMap model) {
        Company company = companyService.findById(id);
        model.addAttribute("company", company);
        return "company/company-add";
    }

}
