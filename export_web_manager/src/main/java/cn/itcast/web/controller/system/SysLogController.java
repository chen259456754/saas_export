package cn.itcast.web.controller.system;

import cn.itcast.domain.system.SysLog;
import cn.itcast.service.system.SysLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/system/log")
public class SysLogController extends BaseController {
    @Resource
    SysLogService sysLogService;

    @RequestMapping(path = "/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        String companyId = getLoginCompanyId();
        PageInfo<SysLog> pageInfo = sysLogService.findByPage(companyId, pageNum, pageSize);
        request.setAttribute("pageInfo", pageInfo);
        return "system/log/log-list";
    }
}
