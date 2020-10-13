package cn.itcast.domain.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-10-13 14:13:15
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -89797012976638991L;

    private String id;

    private String deptId;

    private String email;
    /**
     * 不能重复,可为中文
     */
    private String userName;

    private String station;
    /**
     * shiro MD5密码32位
     */
    private String password;
    /**
     * 1启用0停用
     */
    private Double state;

    private String companyId;

    private String companyName;

    private String deptName;

    private String managerId;

    private String gender;

    private String telephone;

    private String birthday;

    /**
     * 0作为内部控制，租户不能使用
     * 0-saas管理业
     * 1-企业管理员
     * 2-管理所有下属部门和人员
     * 3-管理本部门
     * 4-管理员工
     */
    private Integer degree;

    private Double salary;

    private String joinDate;

    private Integer orderNo;
    /**
     * 登录人编号
     */
    private String createBy;
    /**
     * 登录人所属部门编号
     */
    private String createDempt;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;


}