package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.ModuleService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/system/module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;


    /**
     * 模块列表分页
     * 请求地址：http://localhost:8080/module/list
     * 请求参数：当前页码（pageNum），页面显示数量（pageSize）
     * 响应地址：/WEB-INF/pages/system/module/module-list.jsp
     */
    @RequestMapping(path = "list")
    public ModelAndView list(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "5") int pageSize) {

        PageInfo<Module> pageInfo = moduleService.findByPage(pageNum, pageSize);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("system/module/module-list");
        return mv;
    }

    /**
     * 点击新建，进入添加页面
     * <p>
     * 功能入口：module-list.jsp点击新建
     * 请求地址：http://localhost:8080/module/toAdd
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/system/module/module-add.jsp
     */
    @RequestMapping(path = "toAdd")
    public ModelAndView toAdd() {
        List<Module> moduleList = moduleService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("menus", moduleList);
        mv.setViewName("system/module/module-add");
        return mv;
    }

    /**
     * 点击保存，进行添加
     * <p>
     * 功能入口：module-add.jsp点击保存
     * 请求地址：http://localhost:8080/module/edit
     * 请求参数：无
     * 响应地址：/system/module/list
     */
    @RequestMapping(path = "edit")
    public String edit(Module module) {
        if (StringUtil.isEmpty(module.getId())) {
            moduleService.save(module);
        } else {
            moduleService.update(module);
        }
        return "redirect:/system/module/list";
    }

    /**
     * 点击编辑，进行进入添加页面，修改
     * 1.获取id
     * 2.根据id进行查询
     * 3.查询所有模块
     * 4.保存到请求域中
     * 5.跳转到修改页面
     * <p>
     * 功能入口：module-list.jsp点击编辑
     * 请求地址：http://localhost:8080/module/toUpdate
     * 请求参数：无
     * 响应地址：/WEB-INF/pages/system/module/module-update.jsp
     */
    @RequestMapping(path = "/toUpdate")
    public String toUpdate(String id) {
        List<Module> moduleList = moduleService.findAll();
        Module module = moduleService.findById(id);
        request.setAttribute("menus", moduleList);
        request.setAttribute("module", module);
        return "system/module/module-update";

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
    @ResponseBody
    public Map<String,Object> delete(String id) {
        Map<String,Object> map = new HashMap<>();
        boolean flag = moduleService.delete(id);
        if (flag) {
            map.put("message", "删除成功");
        } else {
            map.put("message", "当前删除的记录被外键引用，删除失败");
        }
        return map;
    }

}
