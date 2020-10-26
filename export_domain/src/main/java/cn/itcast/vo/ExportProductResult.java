package cn.itcast.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@Data
@XmlRootElement(name="products")
public class ExportProductResult implements Serializable {
	private String exportProductId;
	private Double tax;

}
