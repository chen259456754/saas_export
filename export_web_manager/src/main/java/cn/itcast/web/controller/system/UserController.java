package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(path = "/system/user")
public class UserController extends BaseController {
    @Resource
    UserService userService;
    @Resource
    DeptService deptService;

    /**
     * 用户列表分页
     * 请求地址：http://localhost:8080/user/list
     * 请求参数：当前页码（pageNum），页面显示数量（pageSize）
     * 响应地址：/WEB-INF/pages/system/user/user-list.jsp
     */
    @RequestMapping(path = "/list")
    public String list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "5") int pageSize) {
        //获取所属公司id
        String companyId = getLoginCompanyId();
        //调用服务层方法获取页面对象
        PageInfo<User> pageInfo = userService.findAll(companyId, pageNum, pageSize);
        //将页面对象放入请求域
        request.setAttribute("pageInfo", pageInfo);
        return "system/user/user-list";
    }

    /**
     * 进入新增用户界面
     */
    @RequestMapping(path = "/toAdd")
    public String toAdd() {
        String companyId = getLoginCompanyId();
        //查询所有的部门
        List<Dept> deptList = deptService.findAll(companyId);
        //放入请求域中
        request.setAttribute("deptList", deptList);
        return "system/user/user-add";
    }

    /**
     * 点击保存，进行添加
     * <p>
     * 功能入口：在user-add.jsp点击保存
     * 请求地址：http://localhost:8080/user/edit
     * 请求参数：用户信息
     * 响应地址：/system/user/list
     */
    @RequestMapping(path = "/edit")
    public String edit(User user) {
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if (StringUtil.isEmpty(user.getId())) {
            userService.save(user);
        } else {
            userService.update(user);
        }
        return "redirect:/system/user/list";
    }

    /**
     * 点击user-list.jsp编辑，进行修改
     * <p>
     * 功能入口：在user-list.jsp点击编辑
     * 请求地址：http://localhost:8080/user/toUpdate
     * 请求参数：根据用户id更新
     * 响应地址：/WEB-INF/pages/system/user/user-update.jsp
     */
    @RequestMapping(path = "/toUpdate")
    public ModelAndView toUpdate(String id) {
        User user = userService.findById(id);
        String companyId = getLoginCompanyId();
        List<Dept> deptList = deptService.findAll(companyId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.addObject("deptList", deptList);
        mv.setViewName("system/user/user-update");
        return mv;
    }

    /**
     * 删除用户
     * <p>
     * 功能入口：在user-list.jsp 勾选用户后点击删除
     * 请求地址：http://localhost:8080/user/delete
     * 请求参数：根据用户id删除
     * 响应地址：/system/user/list
     */
    @RequestMapping(path = "/delete")
    public String delete(String id) {
        userService.delete(id);
        return "redirect:/system/user/list";
    }
}
