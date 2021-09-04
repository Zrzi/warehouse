package com.database.warehouse.service;

import com.database.warehouse.entity.ProductApplication;
import com.database.warehouse.entity.ProductStoration;
import com.database.warehouse.entity.Warehouse;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.ProductApplicationNotFound;
import com.database.warehouse.mapper.ProductApplicationMapper;
import com.database.warehouse.mapper.ProductStorationMapper;
import com.database.warehouse.mapper.WarehouseMapper;
import com.database.warehouse.utils.LocalTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductApplicationService {

    @Autowired
    private ProductApplicationMapper productApplicationMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private ProductStorationMapper productStorationMapper;


    public ProductApplication findProductApplicationById(Long id)
            throws ProductApplicationNotFound{
        ProductApplication productApplication =
                productApplicationMapper.selectProductApplicationById(id);
        if (productApplication == null) {
            throw new ProductApplicationNotFound();
        }
        return productApplication;
    }

    public List<ProductApplication> findUnstoredProductApplication() {
        return productApplicationMapper.selectUnstoredProductApplication();
    }

    public Long addProductApplication(Long pid, Integer number) {
        ProductApplication productApplication = new ProductApplication();
        productApplication.setPid(pid).setNumber(number);
        productApplicationMapper.insertProductApplication(productApplication);
        return productApplication.getId();
    }

    public int storeProductApplication(Long id, Long wid, Integer number)
            throws InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        ProductApplication productApplication = productApplicationMapper.selectProductApplicationById(id);
        Integer restNumber = productStorationMapper.selectRestNumber(wid);
        if (productApplication == null || restNumber + number > warehouse.getMax() ||
                productApplication.getNumber() == 0 || number > productApplication.getNumber()) {
            throw new InvalidInput();
        }
        ProductStoration productStoration = new ProductStoration();
        productStoration.setWid(wid).setPid(productApplication.getPid())
                .setTime(LocalTimeString.getLocalTimeNow()).setNumber(number).setRestNumber(number);
        productStorationMapper.insertProductStoration(productStoration);
        int tempNumber = productApplication.getNumber() - number;
        productApplication.setNumber(tempNumber);
        if (tempNumber == 0) {
            productApplication.setStored(1);
        }
        productApplicationMapper.updateProductApplication(productApplication);
        return tempNumber;
    }

    public void removeProductApplication(Long id) {
        productApplicationMapper.deleteProductApplication(id);
    }

}
