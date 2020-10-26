package cn.itcast.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {
    /**
     * 统计生产厂家销量金额
     * @param companyId 用户所属公司
     * @return 返回ECharts需要的数据格式.Map的key是生产厂家，value是销售金额
     */
    List<Map<String,Object>> getFactoryData(String companyId);

    /**
     * 销售排行（统计前5名）
     */
    List<Map<String,Object>>getSellData(String companyId,int top);

    List<Map<String,Object>> online();
}
