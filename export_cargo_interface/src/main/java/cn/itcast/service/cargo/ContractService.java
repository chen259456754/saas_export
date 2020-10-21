package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import com.github.pagehelper.PageInfo;

import java.util.List;
/**
 * 购销合同模块
 */
public interface ContractService {

    /**
     * 分页查询
     *
     * @param contractExample 分页查询的参数
     * @param pageNum         当前页
     * @param pageSize        显示数量
     * @return
     */
    PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize);

    /**
     * 查询所有
     *
     * @param contractExample
     * @return
     */
    List<Contract> findAll(ContractExample contractExample);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Contract findById(String id);

    /**
     * 保存
     *
     * @param contract
     */
    void save(Contract contract);

    /**
     * 修改
     *
     * @param contract
     */
    void update(Contract contract);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);
}
