package cn.itcast.dao.system;

import cn.itcast.domain.system.Dept;

import java.util.List;

public interface DeptDao {


    /**
     * 根据企业id，查询公司所有部门
     *
     * @param companyId 根据企业id查询
     */
    List<Dept> findAll(String companyId);

    /**
     * 根据id查询部门
     */
    Dept findById(String id);
    /**
     * 新增
     */
    void save(Dept dept);
    /**
     * 修改
     */
    void update(Dept dept);

    /**
     * 删除
     */
    void delete(String id);

    List<Dept> findByParentId(String id);
}
