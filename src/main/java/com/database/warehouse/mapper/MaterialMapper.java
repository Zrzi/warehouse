package com.database.warehouse.mapper;

import com.database.warehouse.entity.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialMapper {

    Integer selectNumber();

    List<Material> selectMaterialByWid(@Param("wid") Long wid);

    Material selectMaterialByMid(@Param("mid") Long mid);

    Material selectValidMaterialByMid(@Param("mid") Long mid);

    List<Material> selectMaterials();

    Long selectMidByName(@Param("name") String name);

    Long selectValidMidByName(@Param("name") String name);

    Integer insertMaterial(@Param("material") Material material);

    Integer updateMaterial(@Param("material") Material material);

    Integer deleteMaterial(@Param("mid") Long mid);

    Integer activateMaterial(@Param("material") Material material);

}
