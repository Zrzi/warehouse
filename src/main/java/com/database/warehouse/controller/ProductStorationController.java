package com.database.warehouse.controller;

import com.database.warehouse.entity.ProductApplication;
import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.entity.SailOrder;
import com.database.warehouse.entity.vo.MaterialStorationVO;
import com.database.warehouse.entity.vo.ProductStorationVO;
import com.database.warehouse.exception.EmployeeNotFound;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.ProductApplicationNotFound;
import com.database.warehouse.service.ProductApplicationService;
import com.database.warehouse.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ProductStorationController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductApplicationService productApplicationService;

    @ResponseBody
    @GetMapping("/productStorations")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getProductStorations() {
        try {
            List<ProductStorationVO> productStorations =
                    storeService.getProductStorations();
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("data", productStorations);
            responseData.getData().put("size", productStorations.size());
            return responseData;
        } catch (RuntimeException runtimeException) {
            return ResponseData.fail("出现异常");
        }
    }

    @ResponseBody
    @PostMapping("/storeProduct")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData storeProduct(@RequestParam("id") Long id,
                                     @RequestParam("wid") Long wid,
                                     @RequestParam("number") Integer number,
                                     @RequestParam("price") Double price) {
        try {
            ProductApplication application =
                    productApplicationService.findProductApplicationById(id);
            if (application.getNumber() < number) {
                throw new InvalidInput();
            }
            Integer restNumber = productApplicationService.storeProduct(id, number);
            Long pid = storeService.storeProduct(application.getPid(), wid, number, price);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("id", pid);
            responseData.getData().put("number", restNumber);
            return responseData;
        } catch (ProductApplicationNotFound | InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/removeProduct")
    @PreAuthorize("hasRole('ROLE_SAILS_DEPT')")
    public ResponseData removeProduct(@RequestParam("name") String name,
                                      @RequestParam("pid") Long pid,
                                      @RequestParam("wid") Long wid,
                                      @RequestParam("number") Integer number,
                                      @RequestParam("price") Double price) {
        try {
            storeService.removeProduct(name, wid, pid, number, price);
            return ResponseData.success();
        } catch (EmployeeNotFound | InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/moveProduct")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData moveProduct(@RequestParam("id") Long id,
                                    @RequestParam("oldWid") Long oldWid,
                                    @RequestParam("newWid") Long newWid,
                                    @RequestParam("pid") Long pid,
                                    @RequestParam("time") String time,
                                    @RequestParam("number") Integer number) {
        try {
            storeService.moveProduct(id, oldWid, newWid, pid, number);
            return ResponseData.success();
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
