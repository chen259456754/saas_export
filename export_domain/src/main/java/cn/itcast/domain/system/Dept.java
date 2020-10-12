package cn.itcast.domain.system;

import lombok.Data;

@Data
public class Dept {
    private String id;
    private String deptName;
    private Integer state;
    private String companyId;
    private String companyName;
    /**
     * 当前部门关联了一个父部门对象
     */
    private Dept parent;
}
