package cn.itcast.service.system.impl;

import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 分页查询
     */
    @Override
    public PageInfo<User> findAll(String companyId, int pageNum, int pageSize) {
        //调用startPage方法
        PageHelper.startPage(pageNum, pageSize);
        //查询全部列表
        List<User> list = userDao.findAll(companyId);
        //构造pageBean
        return new PageInfo<>(list);
    }

    /**
     * 保存
     */
    @Override
    public void save(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        userDao.save(user);

    }

    /**
     * 更新
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * 删除
     */
    @Override
    public boolean delete(String id) {
        Long count =userDao.findUserRoleByUserId(id);
        if (count!=null&&count>0){
            return false;
        }else {
            userDao.delete(id);
            return true;
        }

    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }
}
