package com.database.warehouse.mapper;

import com.database.warehouse.entity.EmployeeRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeRoleMapper {

    EmployeeRole selectEmployeeRoleByKey(@Param("eid") Long eid, @Param("rid") Long rid);

    List<EmployeeRole> selectEmployeeRoleByEid(@Param("eid") Long eid);

    Integer insertEmployeeRole(@Param("relation") EmployeeRole employeeRole);

    Integer deleteEmployeeRole(@Param("eid") Long eid, @Param("rid") Long rid);

}
