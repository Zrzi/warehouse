package com.database.warehouse.service;

import com.database.warehouse.entity.Employee;
import com.database.warehouse.entity.Role;
import com.database.warehouse.exception.EmployeeExist;
import com.database.warehouse.exception.EmployeeNotFound;
import com.database.warehouse.exception.UpdateInfoException;
import com.database.warehouse.exception.UsernamePasswordNotMatch;
import com.database.warehouse.mapper.EmployeeMapper;
import com.database.warehouse.mapper.EmployeeRoleMapper;
import com.database.warehouse.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = employeeMapper.selectEmployeeByUsername(s);
        if (employee == null) {
            throw new EmployeeNotFound();
        }
        List<Role> roleList = roleMapper.selectRoleByEid(employee.getEid());
        employee.setRoles(roleList);
        return employee;
    }

    public Employee findEmployeeByEid(Long eid) throws EmployeeNotFound {
        Employee employee = employeeMapper.selectEmployeeByEid(eid);
        if (employee == null) {
            throw new EmployeeNotFound();
        }
        return employee;
    }

    public Long employeeLogin(String username, String password) throws EmployeeNotFound, UsernamePasswordNotMatch {
        Long eid = employeeMapper.selectEidByUsername(username);
        if (eid == null) {
            throw new EmployeeNotFound();
        }
        Employee employee = employeeMapper.selectEmployeeByEid(eid);
        if (!employee.getPassword().equals(password)) {
            throw new UsernamePasswordNotMatch();
        }
        return eid;
    }

    public Long employeeRegister(Employee employee) {
        Long eid = employeeMapper.selectEidByUsername(employee.getUsername());
        if (eid != null) {
            throw new EmployeeExist();
        }
        employeeMapper.insertEmployee(employee);
        return employee.getEid();
    }

    public void updateEmployeeInfo(Employee employee) throws UpdateInfoException {
        Integer result = employeeMapper.updateEmployee(employee);
    }

    public void resetPassword(Long eid, String username, String password, String newPassword)
            throws UpdateInfoException, UsernamePasswordNotMatch {
        Employee employee = employeeMapper.selectEmployeeByEid(eid);
        if (!employee.getUsername().equals(username) || !employee.getPassword().equals(password)) {
            throw new UsernamePasswordNotMatch();
        }
        employee.setPassword(newPassword);
        employeeMapper.updatePassword(employee);
    }

}
