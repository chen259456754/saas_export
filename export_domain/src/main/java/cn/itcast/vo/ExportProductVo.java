package cn.itcast.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement(name="exportProduct")
public class ExportProductVo implements Serializable{
	private String id;	  	
	private String eid;
	
	private String exportProductId;		
/*	@JSONField(serialize=false)
	private Export export ;*/
	private String exportId;	
	private String factoryId;			
	private String productNo;			
	private String packingUnit;			//PCS/SETS
	private Integer cnumber;			
	private Integer boxNum;			
	private Double grossWeight;			
	private Double netWeight;			
	private Double sizeLength;			
	private Double sizeWidth;			
	private Double sizeHeight;			
	private Double exPrice;			//sales confirmation 中的价格（手填）
	private Double price;			
	private Double tax;			//收购单价=合同单价
	private Integer orderNo;

}
