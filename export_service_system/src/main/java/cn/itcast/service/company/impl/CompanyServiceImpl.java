package cn.itcast.service.company.impl;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyDao companyDao;

    /**
     * 查询所有
     */
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    /**
     * 添加
     */
    public void save(Company company) {
        String id = UUID.randomUUID().toString();
        company.setId(id);
        companyDao.save(company);
    }

    /**
     * 修改
     */
    public void update(Company company) {
        companyDao.update(company);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        companyDao.delete(id);
    }

    /**
     * 通过id查找
     */
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    /**
     * 分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 页大小
     * @return 返回封装分页参数的PageInfo对象
     */
    public PageInfo<Company> findByPage(int pageNum, int pageSize) {
        //开始分页,关键代码，之后的第一条查询自动分页
        PageHelper.startPage(pageNum, pageSize);
        //调用dao层，查询数据库
        List<Company> list = companyDao.findAll();
        return new PageInfo<Company>(list);
    }


}
