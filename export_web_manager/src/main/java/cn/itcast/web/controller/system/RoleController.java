package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Role;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.RoleService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/system/role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @Resource
    private DeptService deptService;

    /**
     * 角色列表分页
     * 请求地址：http://localhost:8080/role/list
     * 请求参数：当前页码（pageNum），页面显示数量（pageSize）
     * 响应地址：/WEB-INF/pages/system/role/role-list.jsp
     */
    @RequestMapping(path = "/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {

        String companyId = getLoginCompanyId();
        PageInfo<Role> pageInfo = roleService.findPages(companyId, pageNum, pageSize);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("system/role/role-list");
        return mv;
    }

    /**
     * 点击新建，进入添加页面
     * <p>
     * 功能入口：role-list.jsp点击新建
     * 请求地址：http://localhost:8080/role/toAdd
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/system/role/role-add.jsp
     */
    @RequestMapping(path = "/toAdd")
    public String toAdd() {
        return "system/role/role-add";
    }

    /**
     * 点击保存，进行添加
     * <p>
     * 功能入口：role-add.jsp点击保存
     * 请求地址：http://localhost:8080/role/edit
     * 请求参数：无
     * 响应地址：/system/role/list
     */
    @RequestMapping(path = "/edit")
    public String edit(Role role) {
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if (StringUtil.isEmpty(role.getId())) {
            roleService.save(role);
        } else {
            roleService.update(role);
        }
        return "redirect:/system/role/list";
    }

    /**
     * 点击编辑，进行进入添加页面，修改
     * <p>
     * 功能入口：role-list.jsp点击编辑
     * 请求地址：http://localhost:8080/role/toUpdate
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/system/role/role-update.jsp
     */
    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String id) {
        Role role = roleService.findById(id);
        request.setAttribute("role", role);
        return "system/role/role-update";

    }

    /**
     * 删除用户,异步请求返回结果给浏览器
     * <p>
     * 功能入口：role-list.jsp 勾选用户后点击删除
     * 请求地址：http://localhost:8080/role/delete
     * 请求参数：根据用户id删除
     * 响应地址：/system/role/list
     */
    @RequestMapping(path = "/delete")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list";
    }

}
