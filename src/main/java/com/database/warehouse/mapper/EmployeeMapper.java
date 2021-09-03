package com.database.warehouse.mapper;

import com.database.warehouse.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper {

    Employee selectEmployeeByEid(@Param("eid") Long eid);

    List<Employee> selectEmployees();

    List<Employee> selectEmployeesByDid(@Param("eid") Long did);

    Long selectEidByUsername(@Param("username") String username);

    Employee selectEmployeeByUsername(@Param("username") String username);

    Long selectEidByName(@Param("name") String name);

    Integer insertEmployee(@Param("employee") Employee employee);

    Integer updateEmployee(@Param("employee") Employee employee);

    Integer updatePassword(@Param("employee") Employee employee);

    Integer deleteEmployee(@Param("eid") Long eid);

}
