package com.playGround.mapper;

import com.playGround.model.Land;
import com.playGround.model.LandExample;
import com.playGround.model.LandKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LandMapper {

    /*
    * https://g18e95923230ee4-minsuplayground.adb.ap-chuncheon-1.oraclecloudapps.com/ords/admin/_sdw/
    * admin
    * Rlaalstn12.,.
    * */

    long countByExample(LandExample example);

    int deleteByExample(LandExample example);

    int deleteByPrimaryKey(LandKey key);

    int insert(Land record);

    int insertSelective(Land record);

    List<Land> selectByExample(LandExample example);

    List<Land> selectByCriteria(LandExample land);

    Land selectByPrimaryKey(LandKey key);

    int updateByExampleSelective(@Param("record") Land record, @Param("example") LandExample example);

    int updateByExample(@Param("record") Land record, @Param("example") LandExample example);

    int updateByPrimaryKeySelective(Land record);

    int updateByPrimaryKey(Land record);
}