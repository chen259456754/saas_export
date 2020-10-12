package cn.itcast.test;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class CompanyDaoTest {

    @Autowired
    CompanyDao companyDao;

    @Test
    public void findAllTest(){
        List<Company> list = companyDao.findAll();
        System.out.println(list);
    }
    @Test
    public void save(){
        Company company = new Company();
        company.setId("1111");
        company.setName("test");
        companyDao.save(company);
    }

    @Test
    public void update(){
        Company company = new Company();
        company.setId("1112");
        company.setName("test");
        companyDao.save(company);
        company.setName("abc");
        companyDao.update(company);
    }
}
