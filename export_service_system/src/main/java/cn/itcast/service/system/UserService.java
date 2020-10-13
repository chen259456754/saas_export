package cn.itcast.service.system;

import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

public interface UserService {

    /**
     * 分页查询
     */
    PageInfo<User> findAll(String companyId, int pageNum, int pageSize);

    /**
     * 保存
     */
    void save(User user);

    /**
     * 更新
     */
    void update(User user);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据id查询
     */
    User findById(String id);
}
