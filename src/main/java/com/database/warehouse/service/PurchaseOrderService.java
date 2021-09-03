package com.database.warehouse.service;

import com.database.warehouse.entity.MaterialStoration;
import com.database.warehouse.entity.PurchaseOrder;
import com.database.warehouse.entity.Warehouse;
import com.database.warehouse.entity.vo.PurchaseOrderVO;
import com.database.warehouse.exception.EmployeeNotFound;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.mapper.*;
import com.database.warehouse.utils.LocalTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private MaterialStorationMapper materialStorationMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private HistoryRecordMapper historyRecordMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<PurchaseOrderVO> findUnapprovedPurchaseOrder() {
        return purchaseOrderMapper.selectUnapprovedOrderVO();
    }

    public List<PurchaseOrderVO> findUnstoredPurchaseOrder() {
        return purchaseOrderMapper.selectUnstoredOrderVO();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void approvePurchaseOrder(Long id) throws InvalidInput {
        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectPurchaseOrderById(id);
        if (purchaseOrder != null) {
            purchaseOrder.setApproved(1);
            purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
        } else {
            throw new InvalidInput();
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void disapprovePurchaseOrder(Long id) throws InvalidInput {
        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectPurchaseOrderById(id);
        if (purchaseOrder == null) {
            throw new InvalidInput();
        }
        purchaseOrderMapper.deletePurchaseOrder(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Long addPurchaseOrder(String name, Long mid, Integer number, Double price) {
        Long eid = employeeMapper.selectEidByName(name);
        if (eid == null) {
            throw new EmployeeNotFound();
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setEid(eid).setMid(mid).setNumber(number).setPrice(price);
        purchaseOrder.setTime(LocalTimeString.getLocalTimeNow());
        purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        return purchaseOrder.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int storePurchaseOrder(Long id, Long wid, Integer number)
            throws InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectPurchaseOrderById(id);
        if (purchaseOrder == null || getRestMaterialNumber(wid) + number > warehouse.getMax() ||
                purchaseOrder.getRestNumber() == 0 || number > purchaseOrder.getRestNumber()) {
            throw new InvalidInput();
        }
        MaterialStoration materialStoration =
                new MaterialStoration(purchaseOrder.getMid(), wid, LocalTimeString.getLocalTimeNow(),
                        number, number, purchaseOrder.getPrice());
        materialStorationMapper.insertMaterialStoration(materialStoration);
        int tempNumber = purchaseOrder.getRestNumber() - number;
        purchaseOrder.setRestNumber(tempNumber);
        if (tempNumber == 0) {
            purchaseOrder.setStored(1);
        }
        purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
        return tempNumber;
    }

    private int getRestMaterialNumber(Long wid) {
        List<MaterialStoration> materialStorations =
                materialStorationMapper.selectMaterialStorationByWid(wid);
        int number = 0;
        for (MaterialStoration materialStoration : materialStorations) {
            number += materialStoration.getRestNumber();
        }
        return number;
    }

}
