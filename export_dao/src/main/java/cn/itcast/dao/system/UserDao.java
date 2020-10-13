package cn.itcast.dao.system;

import cn.itcast.domain.system.User;

import java.util.List;

public interface UserDao {

    /**
     * 根据企业id查询全部
     */
    List<User> findAll(String companyId);

    /**
     * 根据id查询
     */
    User findById(String userId);


    /**
     * 保存
     */
    void save(User user);

    /**
     * 更新
     */
    void update(User user);

    /**
     * 删除
     */
    void delete(String id);
}
