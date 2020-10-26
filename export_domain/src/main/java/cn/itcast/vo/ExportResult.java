package cn.itcast.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;
@Data
@XmlRootElement(name="export")
public class ExportResult implements Serializable{
	private String exportId;
	private Integer state;
	private String remark;
	private Set<ExportProductResult> products;

}
