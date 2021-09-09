package com.database.warehouse.controller;

import com.database.warehouse.entity.Employee;
import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.entity.vo.RetrievalRecordVO;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.UpdateUnable;
import com.database.warehouse.service.RetrievalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RetrievalRecordController {

    @Autowired
    private RetrievalRecordService retrievalRecordService;

    @ResponseBody
    @GetMapping("/retrievalRecords")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getRetrievalRecords() {
        List<RetrievalRecordVO> retrievalRecords
                = retrievalRecordService.getRetrievalRecords();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("data", retrievalRecords);
        responseData.getData().put("size", retrievalRecords.size());
        return responseData;
    }

    @ResponseBody
    @PutMapping("/retrievalRecord")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData returnMaterial(@RequestParam("id") Long id,
                                       @RequestParam("number") Integer number,
                                       @RequestParam("state") Integer state,
                                       Authentication authentication) {
        try {
            if (state == 1) {
                Employee employee = (Employee) authentication.getPrincipal();
                retrievalRecordService.returnMaterial(id, number, employee.getEid());
            } else {
                retrievalRecordService.damageMaterial(id, number);
            }
            return ResponseData.success();
        } catch (InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/retrievalRecord")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData checkMaterial(@RequestParam("id") Long id) {
        retrievalRecordService.deleteRetrievalRecord(id);
        return ResponseData.success();
    }

    @ResponseBody
    @PutMapping("/updateCost")
    @PreAuthorize("hasRole('ROLE_FINANCE_DEPT')")
    public ResponseData updateCost() {
        try {
            retrievalRecordService.updateCost();
            return ResponseData.success();
        } catch (UpdateUnable e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
