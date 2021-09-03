package com.database.warehouse.mapper;

import com.database.warehouse.entity.ProductMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMaterialMapper {

    ProductMaterial selectProductMaterialByKey(@Param("pid") Long pid, @Param("mid") Long mid);

    List<ProductMaterial> selectProductMaterialByPid(@Param("pid") Long pid);

    List<ProductMaterial> selectProductMaterialByMid(@Param("mid") Long mid);

    Integer insertProductMaterial(@Param("relation") ProductMaterial productMaterial);

    Integer updateProductMaterial(@Param("relation") ProductMaterial productMaterial);

    Integer deleteProductMaterial(@Param("pid") Long pid, @Param("mid") Long mid);

    Integer deleteProductMaterialByPid(@Param("pid") Long pid);

    Integer deleteProductMaterialByMid(@Param("mid") Long mid);

}
