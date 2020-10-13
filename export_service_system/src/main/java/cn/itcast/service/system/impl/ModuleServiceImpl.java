package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.domain.system.Module;
import cn.itcast.service.system.ModuleService;
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
    public void delete(String id) {
        moduleDao.delete(id);
    }
}
