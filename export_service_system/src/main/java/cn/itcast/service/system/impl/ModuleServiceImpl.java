package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ModuleDao moduleDao;
    @Resource
    private UserService userService;

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    /**
     * 查询全部
     *
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<Module> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Module> moduleList = moduleDao.findAll();
        return new PageInfo<>(moduleList);
    }

    /**
     * 添加
     *
     * @param module
     */
    @Override
    public void save(Module module) {
        String id = UUID.randomUUID().toString();
        module.setId(id);
        moduleDao.save(module);
    }

    /**
     * 更新
     *
     * @param module
     */
    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    /**
     * 查询全部
     */
    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(String id) {
        Long moduleRole = moduleDao.findRoleModuleByModuleId(id);
        if (moduleRole != null && moduleRole > 0) {
            return false;
        } else {
            moduleDao.delete(id);
            return true;
        }
    }

    /**
     * 根据id查询角色所具有的权限信息
     */
    @Override
    public List<Module> findModuleByRoleId(String roleId) {
        return moduleDao.findModuleByRoleId(roleId);
    }

    /**
     * 根据用户id查询用户的权限
     * 1.根据用户id查询用户
     * 2.根据用户的degree等级来判断
     * 3.如果degree为0（内部saas管理员）
     * 根据模块中的belong字段进行查询，belong=“0”；
     * 4.若果degree为1（租用企业的管理员）
     * 根据模块中的belong字段进行查询，belong=“1”；
     * 5.其他的用户类型：
     * 借助RBAC的数据库模型，多表联合查询结果
     */
    @Override
    public List<Module> findModuleByUserId(String userId) {
        //通过用户id，查询用户信息。
        User user = userService.findById(userId);
        //获取用户的degree值
        Integer degree = user.getDegree();
        //若degree为0，则说明为saas管理员，直接查询其所有的权限并返回。
        if (degree == 0) {
            return moduleDao.findByBelong(0);
        }
        //若degree为1，则说明为租用企业管理员，直接查询其所有的权限并返回。
        if (degree == 1) {
            return moduleDao.findByBelong(1);
        }
        //若该用户为其他用户则需要，查询其角色再获取其所有的权限
        return moduleDao.findModuleByUserId(userId);
    }
}
