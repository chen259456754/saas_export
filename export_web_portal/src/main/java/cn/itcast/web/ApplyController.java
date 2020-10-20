package cn.itcast.web;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplyController {
    // 注入dubbo服务接口的代理对象
    // com.alibaba.dubbo.config.annotation.Reference
    @DubboReference
    private CompanyService companyService;

    @PostMapping("/apply")
    public String apply(Company company) {
        try {
            // 远程调用企业管理服务
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
