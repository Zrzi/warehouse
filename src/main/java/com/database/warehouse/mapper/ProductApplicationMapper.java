package com.database.warehouse.mapper;

import com.database.warehouse.entity.ProductApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductApplicationMapper {

    ProductApplication selectProductApplicationById(@Param("id") Long id);

    List<ProductApplication> selectUnstoredProductApplication();

    Integer insertProductApplication(@Param("application") ProductApplication productApplication);

    Integer updateProductApplication(@Param("application") ProductApplication productApplication);

    Integer deleteProductApplication(@Param("id") Long id);

}
