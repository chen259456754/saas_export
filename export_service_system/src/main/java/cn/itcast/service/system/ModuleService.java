package cn.itcast.service.system;

import cn.itcast.domain.system.Module;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {

    /**
     * 根据id查询
     */
    Module findById(String id);

    /**
     * 查询全部
     */
    PageInfo<Module> findByPage(int pageNum, int pageSize);

    /**
     * 添加
     */
    void save(Module module);

    /**
     * 更新
     */
    void update(Module module);

    /**
     * 查询全部
     */
    List<Module> findAll();

    /**
     * 删除
     */
    boolean delete(String id);

    /**
     * 根据id查询角色所具有的权限信息
     */
    List<Module> findModuleByRoleId(String roleId);


    /**
     * 根据用户id查询用户的权限
     */
    List<Module> findModuleByUserId(String userId);
}
