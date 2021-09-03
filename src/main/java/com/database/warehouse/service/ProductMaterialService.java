package com.database.warehouse.service;

import com.database.warehouse.entity.HistoryRecord;
import com.database.warehouse.entity.ProductMaterial;
import com.database.warehouse.mapper.HistoryRecordMapper;
import com.database.warehouse.mapper.ProductMaterialMapper;
import com.database.warehouse.utils.LocalTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductMaterialService {

    @Autowired
    private ProductMaterialMapper productMaterialMapper;
    @Autowired
    private HistoryRecordMapper historyRecordMapper;

    public List<ProductMaterial> findProductMaterialByPid(Long pid) {
        return productMaterialMapper.selectProductMaterialByPid(pid);
    }

    public List<ProductMaterial> findProductMaterialByMid(Long mid) {
        return productMaterialMapper.selectProductMaterialByMid(mid);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void modifyProductMaterial(ProductMaterial productMaterial) {
        Long pid = productMaterial.getPid();
        Long mid = productMaterial.getMid();
        ProductMaterial record =
                productMaterialMapper.selectProductMaterialByKey(pid, mid);
        if (record != null) {
            productMaterialMapper.updateProductMaterial(productMaterial);
        } else {
            productMaterialMapper.insertProductMaterial(productMaterial);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteProductMaterial(Long pid, Long mid) {
        ProductMaterial productMaterial =
                productMaterialMapper.selectProductMaterialByKey(pid, mid);
        if (productMaterial == null) {
            return;
        }
        HistoryRecord historyRecord =
                new HistoryRecord(productMaterial.getType(), productMaterial.toString(),
                        LocalTimeString.getLocalTimeNow());
        historyRecordMapper.insertHistoryRecord(historyRecord);
        productMaterialMapper.deleteProductMaterial(pid, mid);
    }

}
