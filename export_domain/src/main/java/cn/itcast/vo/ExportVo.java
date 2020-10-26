package cn.itcast.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@XmlRootElement(name="export")
public class ExportVo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String exportId;
	private List<ExportProductVo> products = new ArrayList<ExportProductVo>();
	private Date inputDate;			
	private String shipmentPort;			
	private String destinationPort;			
	private String transportMode;			//SEA/AIR
	private String priceCondition;			//FBO/CIF
	private Integer boxNums;			//冗余，为委托服务，一个报运的总箱数
	private Double grossWeights;			//冗余，为委托服务，一个报运的总毛重
	private Double measurements;			//冗余，为委托服务，一个报运的总体积
	private Integer state;			
	private String reason;			
	private Integer orderNo;			
	private Date exportDate;			//申批时间

}
