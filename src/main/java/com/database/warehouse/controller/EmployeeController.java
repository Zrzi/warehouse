package com.database.warehouse.controller;

import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.UpdateInfoException;
import com.database.warehouse.exception.UsernamePasswordNotMatch;
import com.database.warehouse.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ResponseBody
    @PostMapping("/login")
    public ResponseData login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        try {
            if ("".equals(username.trim()) || "".equals(password.trim())) {
                throw new InvalidInput();
            }
            Long eid = employeeService.employeeLogin(username, password);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("eid", eid);
            return responseData;
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/logout")
    public ResponseData logout() {
        return ResponseData.success();
    }

    @ResponseBody
    public ResponseData resetPassword(@RequestParam("eid") Long eid,
                                      @RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      @RequestParam("newPassword") String newPassword) {
        try {
            if ("".equals(username.trim()) || "".equals(password.trim()) || "".equals(newPassword.trim())) {
                throw new InvalidInput();
            }
            employeeService.resetPassword(eid, username, password, newPassword);
            return ResponseData.success();
        } catch (InvalidInput | UsernamePasswordNotMatch | UpdateInfoException e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
