package cn.itcast.test;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.security.provider.MD5;

import java.util.List;
import java.util.UUID;

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
    @Test
    public void test(){
        String username = "test@123.com";
        String password = "123";
        Md5Hash encoding = new Md5Hash(password, username);
        System.out.println(encoding);
    }
}
