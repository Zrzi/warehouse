package com.database.warehouse.controller;

import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @ResponseBody
    @GetMapping("/basicData")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getBasicData() {
        try {
            return dataService.getBasicData();
        } catch (RuntimeException e) {
            return ResponseData.fail("出现异常");
        }
    }

}
