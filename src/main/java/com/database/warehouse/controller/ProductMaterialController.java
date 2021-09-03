package com.database.warehouse.controller;

import com.database.warehouse.entity.ProductMaterial;
import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.service.ProductMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductMaterialController {

    @Autowired
    private ProductMaterialService productMaterialService;

    @ResponseBody
    @GetMapping("/product_material_pid")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getProductMaterialsByPid(@RequestParam("pid") Long pid) {
        List<ProductMaterial> productMaterials =
                productMaterialService.findProductMaterialByPid(pid);
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("productMaterials", productMaterials);
        return responseData;
    }

    @ResponseBody
    @GetMapping("/product_material_mid")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getProductMaterialsByMid(@RequestParam("mid") Long mid) {
        List<ProductMaterial> productMaterials =
                productMaterialService.findProductMaterialByMid(mid);
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("productMaterials", productMaterials);
        return responseData;
    }

    @ResponseBody
    @PutMapping("/product_material")
    public ResponseData addProductMaterial(@RequestParam("pid") Long pid,
                                           @RequestParam("mid") Long mid,
                                           @RequestParam("number") Integer number) {
       try {
           if (number == null || number <= 0){
               throw new InvalidInput();
           }
           ProductMaterial productMaterial = new ProductMaterial(pid, mid, number);
           productMaterialService.modifyProductMaterial(productMaterial);
           return ResponseData.success();
       } catch (InvalidInput e) {
           return ResponseData.fail(e.getMessage());
       }
    }

    @ResponseBody
    @DeleteMapping("/product_material")
    public ResponseData deleteProductMaterial(@RequestParam("pid") Long pid,
                                              @RequestParam("mid") Long mid) {
        productMaterialService.deleteProductMaterial(pid, mid);
        return ResponseData.success();
    }
}
