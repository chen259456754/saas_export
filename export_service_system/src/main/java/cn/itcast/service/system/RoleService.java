package cn.itcast.service.system;

import cn.itcast.domain.system.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    /**
     * 根据id查询
     */
    Role findById(String id);

    /**
     * 查询全部
     */
    PageInfo<Role> findPages(String companyId, int pageNum, int pageSize);

    /**
     * 根据id删除
     */
    boolean delete(String id);

    /**
     * 更新
     */
    void update(Role role);

    /**
     * 添加
     */
    void save(Role role);

    /**
     * 角色的权限分配
     */
    void updateRoleModule(String roleId, String moduleIds);

    /**
     * 查询所有的角色
     *
     * @param companyId
     * @return
     */
    List<Role> findAll(String companyId);

    /**
     * 根据用户的id，查询用户拥有的角色
     *
     * @param userId
     * @return
     */
    List<Role> findUserRole(String userId);
}
