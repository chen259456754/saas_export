package cn.itcast.domain.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author makejava
 * @since 2020-10-13 17:09:22
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 309415299353877245L;

    private String id;

    private String name;

    private String remark;

    private Double orderNo;

    private String createBy;

    private String createDept;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String companyId;

    private String companyName;



}