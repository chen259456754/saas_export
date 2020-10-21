package cn.itcast.test.cargo;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.domain.cargo.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class CargoDaoTest{
    @Autowired
    private FactoryDao factoryDao;

    /**
     * 普通更新, 如果对象某些属性没有设置值，有可能会导致数据丢失。
     * 生成的SQL如下：
     * update co_factory set ctype = ?, full_name = ?, factory_name = ?,
     * contacts = ?, phone = ?, mobile = ?, fax = ?, address = ?, inspector = ?,
     * remark = ?, order_no = ?, state = ?, create_by = ?, create_dept = ?,
     * create_time = ?, update_by = ?,update_time = ?
     * where id = ?
     */
    @Test
    public void update(){
        Factory factory = new Factory();
        factory.setId("999"); // 此id在数据库不存在，这里重点是观察生成的sql
        factory.setCreateTime(new Date());
        factory.setUpdateTime(new Date());
        factory.setState(1);
        factoryDao.updateByPrimaryKeySelective(factory);
    }
}