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
}