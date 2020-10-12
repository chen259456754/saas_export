package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/system/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    /**
     * 部门列表分页
     */
    @RequestMapping(path = "/list")
    public ModelAndView list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        //企业id从当前登录的用户中获取用户的企业，现模拟为1.
        String companyId = "1";
        PageInfo<Dept> pageInfo = deptService.findByPage(companyId, pageNum, pageSize);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("system/dept/dept-list");
        return mv;
    }

    /**
     * 点击新建，进入添加页面
     * <p>
     * 功能入口：在dept-list.jsp点击新建
     * 请求地址：http://localhost:8080/dept/toAdd
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/system/dept/dept-add.jsp
     */
    @RequestMapping(path = "/toAdd")
    public String toAdd(Model model) {
        //企业id从当前登录的用户中获取用户的企业，现模拟为1.
        String companyId = "1";
        //查询所有部门
        List<Dept> deptList = deptService.findAll(companyId);
        //保存
        model.addAttribute("deptList", deptList);
        return "system/dept/dept-add";
    }

    /**
     * 点击保存，进行添加
     * <p>
     * 功能入口：在dept-add.jsp点击保存
     * 请求地址：http://localhost:8080/dept/edit
     * 请求参数：无
     * 响应地址：/system/dept/list
     */
    @RequestMapping(path = "/edit")
    public String edit(Dept dept) {
        //设置企业信息
        dept.setCompanyId("1");
        dept.setCompanyName("传智播客教育股份有限公司");
        //判断保存还是更新
        if (StringUtil.isEmpty(dept.getId())) {
            deptService.save(dept);
        } else {
            deptService.update(dept);
        }
        //重定向到列表
        return "redirect:/system/dept/list";
    }

    /**
     * 点击编辑，进行进入添加页面，修改
     * <p>
     * 功能入口：在dept-list.jsp点击编辑
     * 请求地址：http://localhost:8080/dept/toUpdate
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/system/dept/dept-update.jsp
     */
    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String id, Model model) {
        String companyId = "1";
        //根据部门id查询
        Dept dept = deptService.findById(id);
        //查询所有部门
        List<Dept> deptList = deptService.findAll(companyId);
        model.addAttribute("deptList", deptList);
        model.addAttribute("dept", dept);
        return "system/dept/dept-update";
    }
    /**
     * 5. 删除
     * result = {"message":"删除成功！"}
     */
    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(String id){
        //1. 返回对象
        Map<String,String> result = new HashMap<>();
        //2. 调用service删除
        boolean flag = deptService.delete(id);
        //3. 判断
        if (flag) {
            // 删除成功
            result.put("message","删除成功!");
        } else {
            result.put("message","删除失败：删除的部门有子部门，不能删除！");
        }
        return result;
    }
}
