package com.database.warehouse.mapper;

import com.database.warehouse.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    Integer selectNumber();

    List<Product> selectProductByWid(@Param("wid") Long wid);

    Product selectProductByPid(@Param("pid") Long pid);

    List<Product> selectProducts();

    Long selectPidByName(@Param("name") String name);

    Long selectValidPidByName(@Param("name") String name);

    Integer insertProduct(@Param("product") Product product);

    Integer updateProduct(@Param("product") Product product);

    Integer deleteProduct(@Param("pid") Long pid);

    Integer activateProduct(@Param("product") Product product);

}
