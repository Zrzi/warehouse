package com.database.warehouse.controller;

import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.entity.Warehouse;
import com.database.warehouse.exception.*;
import com.database.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @ResponseBody
    @GetMapping("/material_warehouses")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getMaterialWarehouses() {
        List<Warehouse> warehouses = warehouseService.findMaterialWarehouses();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("warehouses", warehouses);
        responseData.getData().put("size", warehouses.size());
        return responseData;
    }

    @ResponseBody
    @GetMapping("/product_warehouses")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getProductWarehouses() {
        List<Warehouse> warehouses = warehouseService.findProductWarehouses();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("warehouses", warehouses);
        responseData.getData().put("size", warehouses.size());
        return responseData;
    }

    @ResponseBody
    @GetMapping("/warehouses")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getWarehouses() {
        List<Warehouse> warehouses = warehouseService.findWarehouse();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("warehouses", warehouses);
        responseData.getData().put("size", warehouses.size());
        return responseData;
    }

    @ResponseBody
    @PostMapping("/warehouse")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData addWarehouse(@RequestParam("type") String type,
                                     @RequestParam("address") String address) {
        try {
            if ("".equals(type.trim()) || "".equals(address.trim())) {
                throw new InvalidInput();
            }
            Warehouse warehouse = new Warehouse();
            warehouse.setType(type);
            warehouse.setAddress(address);
            Long wid = warehouseService.addWarehouse(warehouse);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("wid", wid);
            return responseData;
        } catch (InvalidInput | WarehouseExist e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/warehouse/min")
    @PreAuthorize("hasRole('ROLE_FINANCE_DEPT')")
    public ResponseData resetMin(@RequestParam("wid") Long wid,
                                 @RequestParam("min") Integer min) {
        try {
            warehouseService.setMin(wid, min);
            return ResponseData.success();
        } catch (WarehouseNotExist | InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/warehouse/max")
    @PreAuthorize("hasRole('ROLE_FINANCE_DEPT')")
    public ResponseData resetMax(@RequestParam("wid") Long wid,
                                 @RequestParam("max") Integer max) {
        try {
            warehouseService.setMax(wid, max);
            return ResponseData.success();
        } catch (WarehouseNotExist | InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/warehouse/type")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData resetType(@RequestParam("wid") Long wid,
                                  @RequestParam("type") String type) {
        try {
            warehouseService.resetType(type, wid);
            return ResponseData.success();
        } catch (WarehouseNotExist | ModifyUnable | InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/warehouse")
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_ADMIN')")
    public ResponseData deleteWarehouse(@RequestParam("wid") Long wid) {
        try {
            warehouseService.removeWarehouse(wid);
            return ResponseData.success();
        } catch (DeleteUnable e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
