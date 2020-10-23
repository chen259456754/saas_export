package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ContractProductDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@DubboService
public class ContractProductServiceImpl implements ContractProductService {

    @Resource
    private ContractProductDao contractProductDao;
    @Resource
    private ContractDao contractDao;
    @Resource
    private ExtCproductDao extCproductDao;

    /**
     * 分页查询
     *
     * @param contractProductExample 分页查询的参数
     * @param pageNum                当前页
     * @param pageSize               页大小
     * @return
     */
    @Override
    public PageInfo<ContractProduct> findByPage(ContractProductExample contractProductExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContractProduct> list = contractProductDao.selectByExample(contractProductExample);
        return new PageInfo<>(list);
    }

    /**
     * 查询所有
     *
     * @param contractProductExample
     */
    @Override
    public List<ContractProduct> findAll(ContractProductExample contractProductExample) {
        return contractProductDao.selectByExample(contractProductExample);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    /**
     * 添加货物
     *
     * @param contractProduct
     */
    @Override
    public void save(ContractProduct contractProduct) {
        //设置id
        String id = UUID.randomUUID().toString();
        contractProduct.setId(id);
        double amount = 0d;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        //设置金额
        contractProduct.setAmount(amount);

        //修改购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //修改总金额（加上此次货物的金额）
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        //修改货物数量（原有数量+1）
        contract.setProNum(contract.getProNum() + 1);
        //更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);

        //添加货物
        contractProductDao.insertSelective(contractProduct);
    }

    /**
     * 修改
     *
     * @param contractProduct
     */
    @Override
    public void update(ContractProduct contractProduct) {
        //计算就该后的金额
        Double amount = 0d;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        contractProduct.setAmount(amount);

        //获取修改前的货物金额，查询数据库
        ContractProduct contractProductOld = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        //获得修改前的总金额
        Double oldAmount = contractProductOld.getAmount();

        //修改购销合同中的 总金额。
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount() + amount - oldAmount);
        contractDao.updateByPrimaryKeySelective(contract);

        contractProductDao.updateByPrimaryKeySelective(contractProduct);
    }

    /**
     * 删除部门
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        //根据货物id 查询货物
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        Double cpAmount = contractProduct.getAmount();

        //根据货物id查询附件及累加附件金额、附件数量；删除附件
        ExtCproductExample extExample = new ExtCproductExample();
        //查询条件：contractProductId 货物id
        extExample.createCriteria().andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extExample);
        //获得附件的总金额
        Double extAmount = 0d;
        if (extCproductList != null && extCproductList.size() > 0) {
            for (ExtCproduct extCproduct : extCproductList) {
                //遍历累加获得金额
                extAmount += extCproduct.getAmount();
                //遍历删除
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }

        //购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //修改购销合同的总金额 分别减去货物及附件的金额
        contract.setTotalAmount(contract.getTotalAmount() - cpAmount - extAmount);
        //修改购销合同的货物数量 -1
        contract.setProNum(contract.getProNum() - 1);
        //获得该货物所需附件的数量 若为null则赋值0
        int extNum = extCproductList == null ? 0 : extCproductList.size();
        //修改购销合同的附件数量
        contract.setExtNum(contract.getExtNum() - extNum);
        //更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
        //删除该货物
        contractProductDao.deleteByPrimaryKey(id);
    }
}
