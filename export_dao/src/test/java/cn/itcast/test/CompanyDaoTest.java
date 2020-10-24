package cn.itcast.test;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.company.CompanyDao;
import cn.itcast.dao.system.DeptDao;
import cn.itcast.domain.company.Company;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class CompanyDaoTest {

    @Autowired
    CompanyDao companyDao;
    @Autowired
    ContractDao contractDao;
    @Autowired
    DeptDao deptDao;

    @Test
    public void findAllTest() {
        List<Company> list = companyDao.findAll();
        System.out.println(list);
    }

    @Test
    public void save() {
        Company company = new Company();
        company.setId("1111");
        company.setName("test");
        companyDao.save(company);
    }

    @Test
    public void update() {
        Company company = new Company();
        company.setId("1112");
        company.setName("test");
        companyDao.save(company);
        company.setName("abc");
        companyDao.update(company);
    }

    @Test
    public void test() {
        String username = "lw@export.com";
        String password = "123";
        Md5Hash encoding = new Md5Hash(password, username);
        System.out.println(encoding);
    }

    @Test
    public void test02() {
        Set<String> set = new HashSet<>();
        Set<String> a = new HashSet<>();
        a.add(null);
        System.out.println(a);
    }


}

