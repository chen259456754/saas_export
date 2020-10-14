package cn.itcast.service.system;

import cn.itcast.domain.system.Role;
import com.github.pagehelper.PageInfo;

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
     * 角色的权限分配
     */
    void updateRoleModule(String roleId, String moduleIds);
}
