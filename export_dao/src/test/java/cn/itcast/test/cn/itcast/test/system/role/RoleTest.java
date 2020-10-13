package cn.itcast.test.cn.itcast.test.system.role;

import cn.itcast.dao.system.RoleDao;
import cn.itcast.domain.system.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class RoleTest {
    @Autowired
    private RoleDao roleDao;

    @Test
    public void findAll(){
        List<Role> list = roleDao.findAll("1");
        for (Role role : list) {
            System.out.println(role);
        }
    }
}
