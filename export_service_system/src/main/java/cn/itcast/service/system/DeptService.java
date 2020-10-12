package cn.itcast.service.system;

import cn.itcast.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {

    /**
     * 分页查询所有部门
     *
     * @param companyId 根据所属企业查询
     * @param pageNum   当前页
     * @param pageSize  页大小
     */
    PageInfo<Dept> findByPage(String companyId, int pageNum, int pageSize);


    /**
     * 查询所有的部门
     *
     * @param companyId 公司的id
     */
    List<Dept> findAll(String companyId);

    /**
     * 新增
     */
    void save(Dept dept);

    /**
     * 修改
     */
    void update(Dept dept);

    /**
     * 根据id查询
     */
    Dept findById(String id);

    //-- 删除部门
    //-- 1） 先根据删除的部门是否有子部门，如果有子部门不能删除
    //SELECT * FROM pe_dept WHERE parent_id='100'
    //-- 2） 如果有查询到数据，不能删除，给予提示
    //DELETE FROM pe_dept WHERE dept_id='100'

    /**
     * 删除部门
     * - 1） 先根据删除的部门是否有子部门，如果有子部门不能删除
     * -- 2） 如果有查询到数据，不能删除，给予提示
     */
    boolean delete(String id);
}
