package com.database.warehouse.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CostMapper {

    Double selectCost(@Param("date") String date);

    Integer selectIsChecked(@Param("date") String date);

    Integer insertCost(@Param("date") String date, @Param("cost") Double cost);

    Integer updateCost(@Param("date") String date, @Param("cost") Double cost);

    Integer checkCost(@Param("date") String date);

}
