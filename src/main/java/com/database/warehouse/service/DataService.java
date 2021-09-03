package com.database.warehouse.service;

import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.mapper.*;
import com.database.warehouse.utils.LocalTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataService {

    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private CostMapper costMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseData getBasicData() {
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("warehouse", warehouseMapper.selectNumber());
        responseData.getData().put("material", materialMapper.selectNumber());
        responseData.getData().put("product", productMapper.selectNumber());
        responseData.getData().put("department", departmentMapper.selectNumber());
        Double cost = costMapper.selectCost(LocalTimeString.getCostString());
        responseData.getData().put("cost",
                (cost == null) ? 0 : cost);
        return responseData;
    }

}
