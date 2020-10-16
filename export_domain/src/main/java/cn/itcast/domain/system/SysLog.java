package cn.itcast.domain.system;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class SysLog implements Serializable {
    private String id;

    private String userName;

    private String ip;

    private Date time;

    private String method;

    private String action;

    private String companyId;

    private String companyName;

    private static final long serialVersionUID = 1L;
}