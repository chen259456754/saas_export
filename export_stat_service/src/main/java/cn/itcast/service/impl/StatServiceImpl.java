package cn.itcast.service.impl;

import cn.itcast.dao.stat.StatDao;
import cn.itcast.service.stat.StatService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@DubboService
public class StatServiceImpl implements StatService {
    @Resource
    private StatDao statDao;

    /**
     * 统计生产厂家销量金额
     *
     * @param companyId 用户所属公司
     * @return 返回ECharts需要的数据格式.Map的key是生产厂家，value是销售金额
     */
    @Override
    public List<Map<String, Object>> getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }

    /**
     * 销售排行（统计前5名）
     *
     * @param companyId
     */
    @Override
    public List<Map<String, Object>> getSellData(String companyId, int top) {
        return statDao.getSellData(companyId, top);
    }

    /**
     * 需求3：按小时统计访问次数
     */
    @Override
    public List<Map<String, Object>> online() {
        return statDao.online();
    }
}
