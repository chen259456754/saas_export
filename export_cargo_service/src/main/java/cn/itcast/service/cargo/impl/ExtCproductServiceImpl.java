package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.service.cargo.ExtCproductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@DubboService
public class ExtCproductServiceImpl implements ExtCproductService {
    @Resource
    private ExtCproductDao extCproductDao;
    @Resource
    private ContractDao contractDao;

    /**
     * 分页查询
     *
     * @param extCproductExample
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<ExtCproduct> findByPage(ExtCproductExample extCproductExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExtCproduct> list = extCproductDao.selectByExample(extCproductExample);
        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     *
     * @param extCproductExample
     */
    @Override
    public List<ExtCproduct> findAll(ExtCproductExample extCproductExample) {
        return extCproductDao.selectByExample(extCproductExample);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param extCproduct
     */
    @Override
    public void save(ExtCproduct extCproduct) {
        //设置id
        String id = UUID.randomUUID().toString();
        extCproduct.setId(id);
        //计算总金额
        Double amount = 0d;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //保存附件的总金额
        extCproduct.setAmount(amount);
        //保存附件
        extCproductDao.insertSelective(extCproduct);
        //根据附件查询购销合同
        String contractId = extCproduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //设置总金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        //设置数量
        contract.setExtNum(contract.getExtNum() + 1);
        //保存更新
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 修改
     *
     * @param extCproduct
     */
    @Override
    public void update(ExtCproduct extCproduct) {
        //获取之前的附件金额
        ExtCproduct oleExtCproduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //计算修改之后的金额
        Double amount = 0d;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        extCproduct.setAmount(amount);
        //更新附件
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        //查找购销合同
        Contract contract = contractDao.selectByPrimaryKey(oleExtCproduct.getId());
        contract.setTotalAmount(contract.getTotalAmount() + amount - oleExtCproduct.getAmount());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        //根据id查询到删除的附件对象
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        //查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //修改购销合同的总金额和附件数量
        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
        contract.setExtNum(contract.getExtNum() - 1);
        //更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
        //删除附件
        extCproductDao.deleteByPrimaryKey(id);
    }
}
