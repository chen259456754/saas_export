package cn.itcast.service.system.impl;

import cn.itcast.dao.system.SysLogDao;
import cn.itcast.domain.system.SysLog;
import cn.itcast.service.system.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogDao sysLogDao;

    /**
     * 分页查询所有
     *
     * @param companyId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<SysLog> findByPage(String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysLog> logList = sysLogDao.findAll(companyId);
        return new PageInfo<>(logList);
    }

    /**
     * 保存
     *
     * @param log
     */
    @Override
    public void save(SysLog log) {
        String id = UUID.randomUUID().toString();
        log.setId(id);
        sysLogDao.save(log);
    }
}
