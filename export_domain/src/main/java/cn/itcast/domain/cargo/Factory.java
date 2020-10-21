package cn.itcast.domain.cargo;

import cn.itcast.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 生产厂家的实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Factory extends BaseEntity implements Serializable{

	private String id;
	private String ctype;			//厂家类型：货物/附件
	private String fullName;		//厂家全称
	private String factoryName;		//厂家简称
	private String contacts;		//联系人
	private String phone;			//电话
	private String mobile;			//手机
	private String fax;				//传真
	private String address;			//地址
	private String inspector;		//验货员，杰信代表
	private String remark;			//说明
	private Integer orderNo;		//排序号
	private Integer state;			//状态：1正常0停用(伪删除)
	

}
