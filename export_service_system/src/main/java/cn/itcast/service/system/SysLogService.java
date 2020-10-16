package cn.itcast.service.system;

import cn.itcast.domain.system.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService {

    /**
     * 分页查询所有
     *
     * @param companyId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<SysLog> findByPage(String companyId, int pageNum, int pageSize);

    /**
     * 保存
     *
     * @param log
     */
    void save(SysLog log);
}
