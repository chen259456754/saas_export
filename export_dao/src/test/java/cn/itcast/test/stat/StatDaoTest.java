package cn.itcast.test.stat;

import cn.itcast.dao.stat.StatDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class StatDaoTest {
    @Autowired
    private StatDao statDao;

    @Test
    public void testUpdate() {
        List<Map<String, Object>> list = statDao.getFactoryData("1");
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
    }
    @Test
    public void test(){
        List<Map<String, Object>> list = statDao.getSellData("1",5);
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
    }
    @Test
    public void test2(){
        List<Map<String, Object>> list = statDao.online();
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
    }
}
