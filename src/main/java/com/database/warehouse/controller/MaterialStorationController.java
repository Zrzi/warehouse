package com.database.warehouse.controller;

import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.entity.vo.MaterialStorationVO;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MaterialStorationController {

    @Autowired
    private StoreService storeService;

    @ResponseBody
    @GetMapping("/materialStorations")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getMaterialStorations() {
        try {
            List<MaterialStorationVO> materialStorations =
                    storeService.getMaterialStorations();
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("data", materialStorations);
            responseData.getData().put("size", materialStorations.size());
            return responseData;
        } catch (RuntimeException runtimeException) {
            return ResponseData.fail("出现异常");
        }
    }

    @ResponseBody
    @PostMapping("/storeMaterial")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData storeMaterial(@RequestParam("mid") Long mid,
                                      @RequestParam("wid") Long wid,
                                      @RequestParam("number") Integer number,
                                      @RequestParam("price") Double price) {
        try {
            Long id = storeService.storeMaterial(mid, wid, number, price);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("id", id);
            return responseData;
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/removeMaterial")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData removeMaterial(@RequestParam("mid") Long mid,
                                       @RequestParam("wid") Long wid,
                                       @RequestParam("number") Integer number,
                                       @RequestParam("fifo") Boolean fifo) {
        try {
            storeService.removeMaterial(mid, wid, number, fifo);
            return ResponseData.success();
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/moveMaterial")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData moveMaterial(@RequestParam("oldWid") Long oldWid,
                                     @RequestParam("newWid") Long newWid,
                                     @RequestParam("mid") Long mid,
                                     @RequestParam("time") String time,
                                     @RequestParam("number") Integer number) {
        try {
            storeService.moveMaterial(oldWid, newWid, mid, time, number);
            return ResponseData.success();
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
