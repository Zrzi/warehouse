package com.database.warehouse.controller;

import com.database.warehouse.entity.Employee;
import com.database.warehouse.entity.PurchaseOrder;
import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.entity.vo.PurchaseOrderVO;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @ResponseBody
    @GetMapping("/unapprovedPurchaseOrder")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getUnapprovedPurchaseOrders() {
        List<PurchaseOrderVO> unapprovedPurchaseOrder =
                purchaseOrderService.findUnapprovedPurchaseOrder();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("data", unapprovedPurchaseOrder);
        responseData.getData().put("size", unapprovedPurchaseOrder.size());
        return responseData;
    }

    @ResponseBody
    @GetMapping("/unstoredPurchaseOrder")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getUnstoredPurchaseOrder() {
        List<PurchaseOrderVO> unstoredPurchaseOrder =
                purchaseOrderService.findUnstoredPurchaseOrder();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("data", unstoredPurchaseOrder);
        responseData.getData().put("size", unstoredPurchaseOrder.size());
        return responseData;
    }

    @ResponseBody
    @PostMapping("/purchaseOrder")
    @PreAuthorize("hasRole('ROLE_PURCHASE_ORDER_DEPT')")
    public ResponseData insertPurchaseOrder(@RequestParam("mid") Long mid,
                                            @RequestParam("number") Integer number,
                                            @RequestParam("price") Double price,
                                            Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();
        Long eid = employee.getEid();
        Long id = purchaseOrderService.addPurchaseOrder(eid, mid, number, price);
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("id", id);
        return responseData;
    }

    @ResponseBody
    @PutMapping("/approvePurchaseOrder")
    @PreAuthorize("hasRole('ROLE_PURCHASE_ORDER_ADMIN')")
    public ResponseData approvePurchaseOrder(@RequestParam("id") Long id) {
        try {
            purchaseOrderService.approvePurchaseOrder(id);
            return ResponseData.success();
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/approvePurchaseOrder")
    @PreAuthorize("hasAnyRole('ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN')")
    public ResponseData disapprovePurchaseOrder(@RequestParam("id") Long id) {
        try {
            purchaseOrderService.disapprovePurchaseOrder(id);
            return ResponseData.success();
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/storePurchaseOrder")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData storePurchaseOrder(@RequestParam("id") Long id,
                                           @RequestParam("wid") Long wid,
                                           @RequestParam("number") Integer number) {
        try {
            int restNumber = purchaseOrderService.storePurchaseOrder(id, wid, number);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("number", restNumber);
            return responseData;
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
