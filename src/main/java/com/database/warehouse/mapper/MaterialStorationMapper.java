package com.database.warehouse.mapper;

import com.database.warehouse.entity.MaterialStoration;
import com.database.warehouse.entity.vo.MaterialStorationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialStorationMapper {

    Integer selectRestNumber(@Param("wid") Long wid);

    Integer selectSpecificRestNumber(@Param("wid") Long wid, @Param("mid") Long mid);

    List<MaterialStorationVO> selectMaterialStorationVO(@Param("wid") Long wid);

    MaterialStoration selectMaterialStoration(@Param("wid") Long wid, @Param("mid") Long mid, @Param("time") String time);

    List<MaterialStoration> selectMaterialStorationByKey(@Param("wid") Long wid, @Param("mid") Long mid);

    List<MaterialStoration> selectMaterialStorationByWid(@Param("wid") Long wid);

    List<MaterialStoration> selectMaterialStorationByMid(@Param("mid") Long mid);

    Integer insertMaterialStoration(@Param("storation") MaterialStoration materialStoration);

    Integer updateMaterialStoration(@Param("storation") MaterialStoration materialStoration);

    Integer deleteMaterialStoration(@Param("wid") Long wid, @Param("mid") Long mid, @Param("time") String time);

}
