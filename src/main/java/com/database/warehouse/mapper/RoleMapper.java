package com.database.warehouse.mapper;

import com.database.warehouse.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {

    Role selectRoleByRid(@Param("rid") Long rid);

    Long selectRidByName(@Param("name") String name);

    List<Role> selectRole();

    List<Role> selectRoleByEid(@Param("eid") Long eid);

    Integer insertRole(@Param("role") Role role);

    Integer updateRole(@Param("role") Role role);

    Integer deleteRole(@Param("rid") Long rid);

}
