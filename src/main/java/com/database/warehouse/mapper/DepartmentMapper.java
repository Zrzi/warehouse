package com.database.warehouse.mapper;

import com.database.warehouse.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {

    Integer selectNumber();

    Department selectDepartmentByDid(@Param("did") Long did);

    List<Department> selectDepartments();

    Long selectDidByName(@Param("name") String name);

    Long selectAdminIdByDid(@Param("did") Long did);

    Integer insertDepartment(@Param("department") Department department);

    Integer updateDepartment(@Param("department") Department department);

    Integer deleteDepartment(@Param("did") Long did);

}
