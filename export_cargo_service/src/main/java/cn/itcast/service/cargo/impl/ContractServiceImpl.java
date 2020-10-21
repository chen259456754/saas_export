package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.service.cargo.ContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboService;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@DubboService
public class ContractServiceImpl implements ContractService {
    @Resource
    private ContractDao contractDao;


    /**
     * 分页查询
     *
     * @param contractExample 分页查询的参数
     * @param pageNum         当前页
     * @param pageSize        显示数量
     * @return
     */
    @Override
    public PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Contract> list = contractDao.selectByExample(contractExample);
        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     *
     * @param contractExample
     * @return
     */
    @Override
    public List<Contract> findAll(ContractExample contractExample) {
        return contractDao.selectByExample(contractExample);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    /**
     * 保存
     *
     * @param contract
     */
    @Override
    public void save(Contract contract) {
        String id = UUID.randomUUID().toString();
        //设置uuid为主键
        contract.setId(id);
        //记录购销合同创建时间
        contract.setCreateTime(new Date());
        //默认状态为草稿
        contract.setState(0);
        //初始化：总金额为0
        contract.setTotalAmount(0d);
        //初始化：货物数、附件数
        contract.setExtNum(0);
        contractDao.insertSelective(contract);
    }

    /**
     * 修改
     *
     * @param contract
     */
    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }
}
