package cn.itcast.test;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class CompanyServiceImplTest {
    @Autowired
    CompanyService service;

    @Test
    public void findAllTest() {
        List<Company> list = service.findAll();
        System.out.println(list);
    }

    @Test
    public void findByPageTest() {
        int pageNum = 1;
        int pageSize = 5;
        PageInfo<Company> pageInfo = service.findByPage(pageNum, pageSize);
        System.out.println(pageInfo);
    }
}
