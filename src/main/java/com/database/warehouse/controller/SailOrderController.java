package com.database.warehouse.controller;

import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.entity.SailOrder;
import com.database.warehouse.exception.DeleteUnable;
import com.database.warehouse.service.ProductApplicationService;
import com.database.warehouse.service.SailOrderService;
import com.database.warehouse.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SailOrderController {

    @Autowired
    private SailOrderService sailOrderService;
    @Autowired
    private ProductApplicationService applicationService;
    @Autowired
    private StoreService storeService;

    @ResponseBody
    @GetMapping("/sailOrders")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getSailOrders() {
        List<SailOrder> sailOrder =
                sailOrderService.findSailOrder();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("data", sailOrder);
        responseData.getData().put("size", sailOrder.size());
        return responseData;
    }

    @ResponseBody
    @DeleteMapping("/sailOrder")
    @PreAuthorize("hasRole('ROLE_SAILS_DEPT')")
    public ResponseData deleteSailOrder(@RequestParam("id") Long id) {
        try {
            SailOrder sailOrder = sailOrderService.removeSailOrder(id);
            applicationService.addProductApplication(sailOrder.getPid(), sailOrder.getNumber());
            return ResponseData.success();
        } catch (DeleteUnable e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
