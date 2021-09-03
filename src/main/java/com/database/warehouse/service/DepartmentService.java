package com.database.warehouse.service;

import com.database.warehouse.entity.Department;
import com.database.warehouse.exception.DepartmentExist;
import com.database.warehouse.exception.DepartmentNotFound;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public Department findDepartmentByDid(Long did) throws DepartmentNotFound {
        Department department = departmentMapper.selectDepartmentByDid(did);
        if (department == null) {
            throw new DepartmentNotFound();
        }
        return department;
    }

    public List<Department> findDepartments() {
        return departmentMapper.selectDepartments();
    }

    public Long addDepartment(Department department) throws InvalidInput, DepartmentExist {
        if (department.getName() == null) {
            throw new InvalidInput();
        }
        Long did = departmentMapper.selectDidByName(department.getName());
        if (did != null) {
            throw new DepartmentExist();
        }
        departmentMapper.insertDepartment(department);
        return department.getDid();
    }

    public void updateDepartment(Department department) {
        departmentMapper.updateDepartment(department);
    }

    public Long findAdminId(Long did) throws DepartmentNotFound {
        Department department = departmentMapper.selectDepartmentByDid(did);
        if (department == null) {
            throw new DepartmentNotFound();
        }
        return department.getEid();
    }

}
