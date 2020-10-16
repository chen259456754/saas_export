package cn.itcast.test.cn.itcast.test.system.sysLog;

import cn.itcast.dao.system.SysLogDao;
import cn.itcast.domain.system.SysLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class SysLogTest {
    @Autowired
    private SysLogDao sysLogDao;

    @Test
    public void findAll() {
        List<SysLog> list = sysLogDao.findAll("1");
        for (SysLog log : list) {
            System.out.println(log);
        }
    }
}
