package com.database.warehouse.mapper;

import com.database.warehouse.entity.ProductStoration;
import com.database.warehouse.entity.vo.ProductStorationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductStorationMapper {

    Integer selectRestNumber(@Param("wid") Long wid);

    Integer selectSpecificRestNumber(@Param("wid") Long wid, @Param("pid") Long pid);

    List<ProductStorationVO> selectProductStorationVO(@Param("wid") Long wid);

    ProductStoration selectProductStoration(@Param("id") Long id);

    List<ProductStoration> selectProductStorationByKey(@Param("wid") Long wid, @Param("pid") Long pid);

    List<ProductStoration> selectProductStorationByWid(@Param("wid") Long wid);

    List<ProductStoration> selectProductStorationByPid(@Param("pid") Long pid);

    Integer insertProductStoration(@Param("storation") ProductStoration productStoration);

    Integer updateProductStoration(@Param("storation") ProductStoration productStoration);

    Integer deleteProductStoration(@Param("id") Long id);

}
