package cn.itcast.dao.company;

import cn.itcast.domain.company.Company;

import java.util.List;
public interface CompanyDao {

    List<Company> findAll();

    void save(Company company);

    void update(Company company);

    void delete(String id );

    Company findById(String id);
}
