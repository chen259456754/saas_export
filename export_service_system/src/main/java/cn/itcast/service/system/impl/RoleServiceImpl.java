package cn.itcast.service.system.impl;

import cn.itcast.dao.system.RoleDao;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    /**
     * 根据id查询
     */
    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    /**
     * 查询全部
     */
    @Override
    public PageInfo<Role> findPages(String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleDao.findAll(companyId);
        return new PageInfo<>(roleList);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    /**
     * 更新
     *
     * @param role
     */
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    /**
     * 添加
     *
     * @param role
     */
    @Override
    public void save(Role role) {
        String id = UUID.randomUUID().toString();
        role.setId(id);
        roleDao.save(role);
    }

    /**
     * 角色的权限分配
     *
     * @param roleId
     * @param moduleIds
     */
    @Override
    public void updateRoleModule(String roleId, String moduleIds) {
        //先删除用户角色表中的数据
        roleDao.deleteRoleModule(roleId);
        if (moduleIds != null) {
            //分割字符串获取moduleId
            String[] ids = moduleIds.split(",");
            for (String id : ids) {
                //遍历数组，实现角色添加模块
                roleDao.saveRoleModule(roleId, id);
            }
        }
    }

    /**
     * 查询所有的角色
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    /**
     * 根据用户的id，查询用户拥有的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findUserRole(String userId) {
        return roleDao.findUserRole(userId);
    }
}
