package com.database.warehouse.controller;

import com.database.warehouse.entity.Material;
import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.exception.DeleteUnable;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.MaterialExist;
import com.database.warehouse.exception.MaterialNotFound;
import com.database.warehouse.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @ResponseBody
    @GetMapping("/materials")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getMaterials() {
        List<Material> materials = materialService.findMaterials();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("materials", materials);
        responseData.getData().put("size", materials.size());
        return responseData;
    }

    @ResponseBody
    @GetMapping("/materialByWid")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getMaterialByWid(@RequestParam("wid") Long wid) {
        List<Material> materials = materialService.findMaterialByWid(wid);
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("materials", materials);
        responseData.getData().put("size", materials.size());
        return responseData;
    }

    @ResponseBody
    @GetMapping("/material")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getMaterialByMid(@RequestParam("mid") Long mid) {
        try {
            Material material = materialService.findMaterialByMid(mid);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("material", material);
            return responseData;
        } catch (MaterialNotFound e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/material")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData addMaterial(@RequestParam("name") String name) {
        try {
            if ("".equals(name.trim())) {
                throw new InvalidInput();
            }
            Material material = new Material();
            material.setName(name);
            Long mid = materialService.addMaterial(material);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("mid", mid);
            return responseData;
        } catch (MaterialExist | InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/material")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData updateMaterial(@RequestParam("mid") Long mid,
                                       @RequestParam("name") String name) {
        try {
            if (mid == null || "".equals(name.trim())) {
                throw new InvalidInput();
            }
            Material material = new Material(mid, name);
            materialService.updateMaterial(material);
            return ResponseData.success();
        } catch (InvalidInput | MaterialExist e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("material")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData deleteMaterial(@RequestParam("mid") Long mid) {
        try {
            materialService.removeMaterial(mid);
            return ResponseData.success();
        } catch (DeleteUnable e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
