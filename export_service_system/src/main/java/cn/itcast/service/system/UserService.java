package cn.itcast.service.system;

import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

public interface UserService {

    /**
     * 分页查询
     */
    PageInfo<User> findAll(String companyId, int pageNum, int pageSize);

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
    boolean delete(String id);

    /**
     * 根据id查询
     */
    User findById(String id);

    /**
     * 实现给用户分配角色
     *
     * @param userId
     * @param roleIds
     */
    void updateUserRoles(String userId, String[] roleIds);

    /**
     * 通过邮箱账号查询用户对象
     *
     * @param email
     * @return
     */
    User findByEmail(String email);
}
