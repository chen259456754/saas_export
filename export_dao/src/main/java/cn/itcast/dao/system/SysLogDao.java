package cn.itcast.dao.system;

import cn.itcast.domain.system.SysLog;

import java.util.List;

public interface SysLogDao {


    /**
     * 查询全部
     *
     * @param companyId
     * @return
     */
    List<SysLog> findAll(String companyId);

    /**
     * 添加
     *
     * @param log
     * @return
     */
    int save(SysLog log);

    int deleteByPrimaryKey(String id);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}