package cn.itcast.service.company;

import cn.itcast.domain.company.Company;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CompanyService {

    /**
     * 查询所有
     */
    List<Company> findAll();

    /**
     * 添加
     */
    void save(Company company);

    /**
     * 修改
     */
    void update(Company company);


    /**
     * 删除
     */
    void delete(String id);

    /**
     * 通过id查找
     */
    Company findById(String id);

    /**
     * 分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 页大小
     * @return 返回封装分页参数的PageInfo对象
     */
    PageInfo<Company> findByPage(int pageNum, int pageSize);
}
