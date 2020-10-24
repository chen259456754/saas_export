package cn.itcast.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContractProductVo implements Serializable {
    /**
     * 客户名称
     */
    private String customName;
    /**
     * 合同号，订单号
     */
    private String contractNo;
    /**
     * 货号
     */
    private String productNo;
    /**
     * 数量
     */
    private Integer cnumber;
    /**
     * 厂家名称
     */
    private String factoryName;
    /**
     * 交货期限
     */
    private Date deliveryPeriod;
    /**
     * 船期
     */
    private Date shipTime;
    /**
     * 贸易条款
     */
    private String tradeTerms;
}
