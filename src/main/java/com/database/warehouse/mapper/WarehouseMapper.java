package com.database.warehouse.mapper;

import com.database.warehouse.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WarehouseMapper {

    Integer selectNumber();

    Warehouse selectWarehouseByWid(@Param("wid") Long wid);

    Warehouse selectWarehouseByAddress(@Param("address") String address);

    List<Warehouse> selectWarehouseByType(@Param("type") String type);

    List<Warehouse> selectWarehouse();

    Integer insertWarehouse(@Param("warehouse") Warehouse warehouse);

    Integer updateWarehouse(@Param("warehouse") Warehouse warehouse);

    Integer deleteWarehouse(@Param("wid") Long wid);

}
