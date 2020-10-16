package cn.itcast.dao.system;

import cn.itcast.domain.system.Module;

import java.util.List;

public interface ModuleDao {

    /**
     * 根据id查询
     */
    Module findById(String id);

    /**
     * 根据id删除
     */
    void delete(String id);

    /**
     * 添加
     */
    void save(Module module);

    /**
     * 更新
     */
    void update(Module module);

    /**
     * 查询所有
     */
    List<Module> findAll();

    /**
     * 根据角色id，查询角色具有的权限
     */
    List<Module> findModuleByRoleId(String roleId);

    /**
     * 根据模块id查询角色模块中间表
     *
     * @param id
     * @return
     */
    Long findRoleModuleByModuleId(String id);

    /**
     * 根据从属belong字段查询用户权限
     *
     * @param belong
     * @return
     */
    List<Module> findByBelong(int belong);

    /**
     * 根据用户id查询其权限
     *
     * @param userId
     * @return
     */
    List<Module> findModuleByUserId(String userId);
}
