package cn.itcast.test.system.dept;

import cn.itcast.dao.system.DeptDao;
import cn.itcast.domain.system.Dept;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class DeptTest {
    @Autowired
    private DeptDao deptDao;

    @Test
    public void findAll(){
        List<Dept> list = deptDao.findAll("1");
        for (Dept dept : list) {
            System.out.println(dept);
        }

    }
    @Test
    public void saveTest(){
        Dept dept = deptDao.findById("83d40765-e2f8-4f3f-918e-eb20984b65b3");
        System.out.println(dept);
    }
}
