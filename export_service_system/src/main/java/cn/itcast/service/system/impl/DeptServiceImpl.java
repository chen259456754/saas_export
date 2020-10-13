package cn.itcast.service.system.impl;

import cn.itcast.dao.system.DeptDao;
import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptDao deptDao;

    /**
     * 分页查询所有部门
     *
     * @param companyId 根据所属企业查询
     * @param pageNum   当前页
     * @param pageSize  页大小
     */
    public PageInfo<Dept> findByPage(String companyId, int pageNum, int pageSize) {
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        //调用dao
        List<Dept> list = deptDao.findAll(companyId);
        return new PageInfo<>(list);
    }

    /**
     * 查询所有的部门
     *
     * @param companyId 公司的id
     */
    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    /**
     * 新增
     */
    @Override
    public void save(Dept dept) {
        String id = UUID.randomUUID().toString();
        dept.setId(id);
        deptDao.save(dept);
    }

    /**
     * 修改
     */
    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    /**
     * 删除部门
     * - 1） 先根据删除的部门是否有子部门，如果有子部门不能删除
     * -- 2） 如果有查询到数据，不能删除，给予提示
     */
    @Override
    public boolean delete(String id) {
        List<Dept> list = deptDao.findByParentId(id);
        //2） 如果有查询到数据，不能删除，给予提示
        if (list != null && list.size() > 0) {
            // 查询到子部门，不能删除
            return false;
        } else {
            // 删除
            deptDao.delete(id);
            return true;
        }
    }
}
