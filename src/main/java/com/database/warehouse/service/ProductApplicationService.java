package com.database.warehouse.service;

import com.database.warehouse.entity.*;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.ProductApplicationNotFound;
import com.database.warehouse.exception.UpdateUnable;
import com.database.warehouse.mapper.*;
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
    @Autowired
    private ProductMaterialMapper productMaterialMapper;
    @Autowired
    private RetrievalRecordMapper retrievalRecordMapper;


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

    @Transactional(rollbackFor = RuntimeException.class)
    public Long addProductApplication(Long pid, Integer number) {
        List<ProductMaterial> relations
                = productMaterialMapper.selectProductMaterialByPid(pid);
        for (ProductMaterial relation : relations) {
            Long mid = relation.getMid();
            int spent = number * relation.getNumber();
            Integer rest = retrievalRecordMapper.selectRestNumberByMid(mid);
            if (rest == null || spent > rest) {
                throw new InvalidInput();
            }
            List<RetrievalRecord> records
                    = retrievalRecordMapper.selectRetrievalRecordByMid(mid);
            for (RetrievalRecord record : records) {
                Integer recordNumber = record.getNumber();
                if (recordNumber < rest) {
                    rest -= recordNumber;
                    retrievalRecordMapper.deleteRetrievalRecord(record.getId());
                } else if (recordNumber.equals(rest)) {
                    retrievalRecordMapper.deleteRetrievalRecord(record.getId());
                    break;
                } else {
                    recordNumber -= rest;
                    record.setNumber(recordNumber);
                    retrievalRecordMapper.updateRetrievalRecord(record);
                    break;
                }
            }
        }
        ProductApplication productApplication = new ProductApplication();
        productApplication.setPid(pid).setNumber(number);
        productApplicationMapper.insertProductApplication(productApplication);
        return productApplication.getId();
    }

    public void addProductApplicationBySailOrder(Long pid, Integer number) {
        ProductApplication productApplication = new ProductApplication();
        productApplication.setPid(pid).setNumber(number);
        productApplicationMapper.insertProductApplication(productApplication);
    }

    public int storeProductApplication(Long id, Long wid, Integer number)
            throws InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        ProductApplication productApplication = productApplicationMapper.selectProductApplicationById(id);
        Integer restNumber = productStorationMapper.selectRestNumber(wid);
        if (restNumber == null) {
            restNumber = 0;
        }
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
