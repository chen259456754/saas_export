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

    /**
     * 根据用户id查询用户角色中间表
     */
    Long findUserRoleByUserId(String id);

    /**
     * 根据用户id删除中间表数据
     *
     * @param userId
     */
    void deleteUserRole(String userId);

    /**
     * 向中间表中保存数据
     *
     * @param userId
     * @param roleId
     */
    void saveUserRole(String userId, String roleId);

    /**
     * 通过邮箱账号查询用户对象
     * @param email
     * @return
     */
    User findByEmail(String email);
}
