package cn.itcast.dao.system;

import cn.itcast.domain.system.Role;

import java.util.List;

public interface RoleDao {

    /**
     * 根据id查询
     */
    Role findById(String id);

    /**
     * 查询全部
     */
    List<Role> findAll(String companyId);

    /**
     * 根据id删除
     */
    void delete(String id);

    /**
     * 更新
     */
    void update(Role role);

    /**
     * 添加
     */
    void save(Role role);

    /**
     * 删除用户角色中间表的数据
     */
    void deleteRoleModule(String roleId);

    /**
     * 角色添加模块
     */
    void saveRoleModule(String roleId, String id);

    List<Role> findUserRole(String userId);

    /**
     * 根据角色id查询用户角色中间表
     *
     * @param id
     * @return
     */
    Long findUserRoleByRoleId(String id);

    /**
     * 根据角色id查询角色模块中间表
     *
     * @param id
     * @return
     */
    Long findRoleModuleByRoleId(String id);
}
