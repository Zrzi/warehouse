package com.database.warehouse.mapper;

import com.database.warehouse.entity.SailOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SailOrderMapper {

    SailOrder selectSailOrderByKey(@Param("id") Long id);

    List<SailOrder> selectSailOrderByEid(@Param("eid") Long eid);

    List<SailOrder> selectSailOrderByPid(@Param("pid") Long pid);

    List<SailOrder> selectSailOrder();

    Integer insertSailOrder(@Param("order") SailOrder sailOrder);

    Integer updateSailOrder(@Param("order") SailOrder sailOrder);

    Integer deleteSailOrder(@Param("id") Long id);

}
