package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.*;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ExportProductResult;
import cn.itcast.vo.ExportResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.*;

@DubboService
public class ExportServiceImpl implements ExportService {
    @Resource
    private ExportDao exportDao;
    @Resource
    private ContractDao contractDao;
    @Resource
    private ContractProductDao contractProductDao;
    @Resource
    private ExportProductDao exportProductDao;
    @Resource
    private ExtCproductDao extCproductDao;
    @Resource
    private ExtEproductDao extEproductDao;

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Export export) {

        String id = UUID.randomUUID().toString();
        export.setId(id);
        //制单时间
        export.setInputDate(new Date());
        //创建时间
        export.setCreateTime(new Date());
        export.setState(0);
        //获得所有购销合同的id
        String contractIds = export.getContractIds();


        //保存购销合同的合同号, 多个合同号用空格隔开; 修改购销合同状态
        String customerContract = "";
        String[] array = contractIds.split(",");
        for (String contractId : array) {
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            customerContract += contract.getContractNo() + " ";
            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);
        }
        //设置报运单合同号
        export.setCustomerContract(customerContract);


        // 根据购销购销合同ID集合，查询货物
        ContractProductExample cpExample = new ContractProductExample();
        ContractProductExample.Criteria criteria = cpExample.createCriteria();
        criteria.andContractIdIn(Arrays.asList(array));
        List<ContractProduct> cpList = contractProductDao.selectByExample(cpExample);
        /*
         * 定义Map存储key是购销合同的货物id，对应的value是出口报运商品的id
         * 为什么？  因为后面保存商品附件，需要根据货物id拿到报运商品id
         */
        Map<String, String> map = new HashMap<>();
        //遍历货物集合，作为出口报运的商品； 并保存商品
        for (ContractProduct cp : cpList) {
            // 创建报运商品对象
            ExportProduct ep = new ExportProduct();
            // 把货物对象属性，拷贝到商品中
            BeanUtils.copyProperties(cp, ep);
            ep.setId(UUID.randomUUID().toString());
            ep.setExportId(export.getId());
            // 保存商品
            exportProductDao.insertSelective(ep);
            // 把货物id、商品id存储到map中
            map.put(cp.getId(), ep.getId());
        }


        //查询购销合同下的所有附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdIn(Arrays.asList(array));
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        for (ExtCproduct extCproduct : extCproductList) {
            // 创建报运商品附件对象
            ExtEproduct extEproduct = new ExtEproduct();
            // 对象拷贝
            BeanUtils.copyProperties(extCproduct, extEproduct);
            // 设置商品附件属性：报运单id、报运商品
            extEproduct.setId(UUID.randomUUID().toString());
            extEproduct.setExportId(export.getId());
            /*
             * 如何获取刚刚保存的出口报运的商品ID?
             * 目前已经条件是：购销合同货物ID 【extCproduct.getContractProductId()】
             * 目标：        获取商品ID
             */
            String exportProductId = map.get(extCproduct.getContractProductId());
            extEproduct.setExportProductId(exportProductId);
            // 保存商品附件
            extEproductDao.insertSelective(extEproduct);
        }

        // 8. 设置报运单属性。保存报运单
        export.setProNum(cpList.size());
        export.setExtNum(extCproductList.size());
        // 保存报运单
        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {
        //更新报运单主体
        exportDao.updateByPrimaryKeySelective(export);
        //循环更新多个商品
        if (export.getExportProducts() != null && export.getExportProducts().size() > 0) {
            for (ExportProduct exportProduct : export.getExportProducts()) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    @Override
    public void delete(String id) {
        exportDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Export> findByPage(ExportExample exportExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Export> list = exportDao.selectByExample(exportExample);
        return new PageInfo<>(list);
    }

    @Override
    public void updateExport(ExportResult result) {
        //1.查询报运单
        Export export = exportDao.selectByPrimaryKey(result.getExportId());
        //2.设置报运单属性（状态，和说明）
        export.setState(result.getState());
        export.setRemark(result.getRemark());
        exportDao.updateByPrimaryKeySelective(export);

        //3.循环处理报运商品
        if (result.getProducts() != null && result.getProducts().size() > 0) {
            for (ExportProductResult epr : result.getProducts()) {
                ExportProduct exportProduct = exportProductDao.selectByPrimaryKey(epr.getExportProductId());
                //4.对报运商品的税收修改
                exportProduct.setTax(epr.getTax());
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }
}
