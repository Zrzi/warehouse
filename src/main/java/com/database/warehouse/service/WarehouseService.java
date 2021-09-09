package com.database.warehouse.service;

import com.database.warehouse.entity.MaterialStoration;
import com.database.warehouse.entity.ProductStoration;
import com.database.warehouse.entity.Warehouse;
import com.database.warehouse.entity.vo.WarehouseVO;
import com.database.warehouse.exception.*;
import com.database.warehouse.mapper.MaterialStorationMapper;
import com.database.warehouse.mapper.ProductStorationMapper;
import com.database.warehouse.mapper.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private ProductStorationMapper productStorationMapper;
    @Autowired
    private MaterialStorationMapper materialStorationMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public List<WarehouseVO> findWarehouse() {
        List<Warehouse> warehouses = warehouseMapper.selectWarehouse();
        List<WarehouseVO> voList = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            WarehouseVO warehouseVO = new WarehouseVO(warehouse);
            Integer number = null;
            if ("原材料仓库".equals(warehouse.getType())) {
                number = materialStorationMapper.selectRestNumber(warehouse.getWid());
            } else if ("产品仓库".equals(warehouse.getType())) {
                number = productStorationMapper.selectRestNumber(warehouse.getWid());
            }
            if (number == null) {
                number = 0;
            }
            warehouseVO.setNumber(number);
            voList.add(warehouseVO);
        }
        return voList;
    }

    public List<Warehouse> findMaterialWarehouses() {
        return warehouseMapper.selectWarehouseByType("原材料仓库");
    }

    public List<Warehouse> findProductWarehouses() {
        return warehouseMapper.selectWarehouseByType("产品仓库");
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Long addWarehouse(Warehouse warehouse) throws WarehouseExist {
        String address = warehouse.getAddress();
        Warehouse record = warehouseMapper.selectWarehouseByAddress(address);
        if (record != null) {
            throw new WarehouseExist();
        }
        warehouse.setMin(0);
        warehouse.setMax(100);
        warehouseMapper.insertWarehouse(warehouse);
        return warehouse.getWid();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void setMin(Long wid, Integer min) throws WarehouseNotExist, InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        if (warehouse == null) {
            throw new WarehouseNotExist();
        }
        String type = warehouse.getType();
        int number;
        if ("产品仓库".equals(type)) {
            number = productStorationMapper.selectRestNumber(wid);
        } else if ("原材料仓库".equals(type)){
            number = materialStorationMapper.selectRestNumber(wid);
        } else {
            throw new InvalidInput();
        }
        if (number < min) {
            throw new InvalidInput();
        }
        warehouse.setMin(min);
        warehouseMapper.updateWarehouse(warehouse);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void setMax(Long wid, Integer max) throws WarehouseNotExist, InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        if (warehouse == null) {
            throw new WarehouseNotExist();
        }
        String type = warehouse.getType();
        Integer number;
        if ("产品仓库".equals(type)) {
            number = productStorationMapper.selectRestNumber(wid);
        } else if ("原材料仓库".equals(type)){
            number = materialStorationMapper.selectRestNumber(wid);
        } else {
            throw new InvalidInput();
        }
        if (number != null && number > max) {
            throw new InvalidInput();
        }
        warehouse.setMax(max);
        warehouseMapper.updateWarehouse(warehouse);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void resetType(String type, Long wid) throws WarehouseNotExist, ModifyUnable, InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        if (warehouse == null) {
            throw new WarehouseNotExist();
        }
        if (!warehouse.getType().equals(type)) {
            Integer number;
            if ("产品仓库".equals(warehouse.getType())) {
                number = productStorationMapper.selectRestNumber(warehouse.getWid());
            } else if ("原材料仓库".equals(warehouse.getType())){
                number = materialStorationMapper.selectRestNumber(warehouse.getWid());
            } else {
                throw new InvalidInput();
            }
            if (number != null && number != 0) {
                throw new ModifyUnable();
            }
            warehouse.setType(type);
            warehouseMapper.updateWarehouse(warehouse);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void removeWarehouse(Long wid) throws DeleteUnable {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        if (warehouse != null) {
            Integer materialNumber = materialStorationMapper.selectRestNumber(wid);
            Integer productNumber = productStorationMapper.selectRestNumber(wid);
            if ((materialNumber != null && materialNumber != 0) ||
                    (productNumber != null && productNumber != 0)) {
                throw new DeleteUnable();
            }
            warehouseMapper.deleteWarehouse(wid);
        }
    }

}
