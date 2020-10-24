package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.ExtEproduct;
import cn.itcast.domain.cargo.ExtEproductExample;

import java.util.List;
public interface ExtEproductService{


    long countByExample(ExtEproductExample example);

    int deleteByExample(ExtEproductExample example);

    int deleteByPrimaryKey(String id);

    int insert(ExtEproduct record);

    int insertSelective(ExtEproduct record);

    List<ExtEproduct> selectByExample(ExtEproductExample example);

    ExtEproduct selectByPrimaryKey(String id);

    int updateByExampleSelective(ExtEproduct record,ExtEproductExample example);

    int updateByExample(ExtEproduct record,ExtEproductExample example);

    int updateByPrimaryKeySelective(ExtEproduct record);

    int updateByPrimaryKey(ExtEproduct record);

}
