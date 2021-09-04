package com.database.warehouse.controller;

import com.database.warehouse.entity.ProductApplication;
import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.service.ProductApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductApplicationController {

    @Autowired
    private ProductApplicationService productApplicationService;

    @ResponseBody
    @GetMapping("/productApplications")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getUnstoredApplications() {
        List<ProductApplication> unstoredProductApplication =
                productApplicationService.findUnstoredProductApplication();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("data", unstoredProductApplication);
        responseData.getData().put("size", unstoredProductApplication.size());
        return responseData;
    }

    @ResponseBody
    @PostMapping("/productApplication")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData addProductApplication(@RequestParam("pid") Long pid,
                                              @RequestParam("number") Integer number) {
        Long id = productApplicationService.addProductApplication(pid, number);
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("id", id);
        return responseData;
    }

    @ResponseBody
    @DeleteMapping("/productApplication")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData deleteProductApplication(@RequestParam("id") Long id) {
        productApplicationService.removeProductApplication(id);
        return ResponseData.success();
    }

    @ResponseBody
    @PostMapping("/storeApplication")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData storeApplication(@RequestParam("id") Long id,
                                         @RequestParam("wid") Long wid,
                                         @RequestParam("number") Integer number) {
        try {
            int restNumber = productApplicationService.storeProductApplication(id, wid, number);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("number", restNumber);
            return responseData;
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
