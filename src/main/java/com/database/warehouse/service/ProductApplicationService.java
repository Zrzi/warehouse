package com.database.warehouse.service;

import com.database.warehouse.entity.ProductApplication;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.ProductApplicationNotFound;
import com.database.warehouse.mapper.ProductApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductApplicationService {

    @Autowired
    private ProductApplicationMapper productApplicationMapper;

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

    @Transactional(rollbackFor = RuntimeException.class)
    public Integer storeProduct(Long id, Integer number)
            throws ProductApplicationNotFound, InvalidInput {
        ProductApplication productApplication =
                productApplicationMapper.selectProductApplicationById(id);
        if (productApplication == null) {
            throw new ProductApplicationNotFound();
        }
        if (productApplication.getNumber() < number) {
            throw new InvalidInput();
        }
        int restNumber = productApplication.getNumber() - number;
        productApplication.setNumber(restNumber);
        if (restNumber == 0) {
            productApplication.setStored(1);
        }
        productApplicationMapper.updateProductApplication(productApplication);
        return restNumber;
    }

    public void removeProductApplication(Long id) {
        productApplicationMapper.deleteProductApplication(id);
    }

}
