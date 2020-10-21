package cn.itcast.domain.cargo;

import cn.itcast.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 合同下货物的实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContractProduct extends BaseEntity implements Serializable {

	private String id;
	private String productNo;		//货号
	private String productImage;	//图片路径
	private String productDesc;		//货描
	private String loadingRate;		//报运：装率    1/3
	private Integer boxNum;			//报运：箱数    100
	private String packingUnit;		//包装单位：PCS/SETS   支/箱
	private Integer cnumber;		//数量                            300
	private Integer outNumber;		//报运：出货数量            200
	private Integer finished;		//报运：是否完成		no
	private String productRequest;	//要求
	private Double price;			//单价
	private Double amount;			//总金额，冗余
	private Integer orderNo;		//排序号
	private String contractId;      //合同号

	private String factoryName;		//厂家名称，冗余字段
	private String factoryId;

	private List<ExtCproduct> extCproducts ;	//货物和附件，一对多

	public ContractProduct(){}

	public ContractProduct(Object []objs, String companyId, String companyName) {
		this.factoryName = objs[1].toString();
		this.productNo = objs[2].toString();
		this.cnumber = ((Double) objs[3]).intValue();
		this.packingUnit = objs[4].toString();
		this.loadingRate = objs[5].toString();
		this.boxNum = ((Double) objs[6]).intValue();
		this.price = (Double) objs[7];			//单价
		this.productRequest=objs[8].toString();
		this.productDesc=objs[9].toString();
		this.companyId = companyId;
		this.companyName = companyName;
	}


	@Override
	public String toString() {
		return "ContractProduct{" +
				"id='" + id + '\'' +
				", productNo='" + productNo + '\'' +
				", productImage='" + productImage + '\'' +
				", productDesc='" + productDesc + '\'' +
				", loadingRate='" + loadingRate + '\'' +
				", boxNum=" + boxNum +
				", packingUnit='" + packingUnit + '\'' +
				", cnumber=" + cnumber +
				", outNumber=" + outNumber +
				", finished=" + finished +
				", productRequest='" + productRequest + '\'' +
				", price=" + price +
				", amount=" + amount +
				", orderNo=" + orderNo +
				", contractId='" + contractId + '\'' +
				", factoryName='" + factoryName + '\'' +
				", factoryId='" + factoryId + '\'' +
				", extCproducts=" + extCproducts +
				'}';
	}
}
