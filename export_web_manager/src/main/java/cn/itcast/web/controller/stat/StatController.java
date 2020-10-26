package cn.itcast.web.controller.stat;

import cn.itcast.service.stat.StatService;
import cn.itcast.web.controller.BaseController;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @DubboReference
    private StatService statService;

    @RequestMapping(path = "/toCharts")
    public String toCharts(String chartsType) {
        return "stat/stat-" + chartsType;
    }

    @RequestMapping(path = "/getFactoryData")
    @ResponseBody
    public List<Map<String, Object>> getFactoryData() {
        return statService.getFactoryData(getLoginCompanyId());
    }

    /**
     * 产品销售排行
     *
     * @return
     */
    @RequestMapping("/getSellData")
    @ResponseBody
    public List<Map<String, Object>> getSellData() {
        return statService.getSellData(getLoginCompanyId(), 5);
    }

    // stat-online.jsp 页面异步请求返回json格式数据
    @RequestMapping("/online")
    @ResponseBody
    public List<Map<String, Object>> online() {
        return statService.online();
    }
}
