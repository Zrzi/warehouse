package com.database.warehouse.service;

import com.database.warehouse.entity.Material;
import com.database.warehouse.exception.DeleteUnable;
import com.database.warehouse.entity.Product;
import com.database.warehouse.entity.ProductMaterial;
import com.database.warehouse.entity.ProductStoration;
import com.database.warehouse.exception.MaterialExist;
import com.database.warehouse.exception.ProductExist;
import com.database.warehouse.exception.ProductNotFound;
import com.database.warehouse.mapper.ProductMapper;
import com.database.warehouse.mapper.ProductMaterialMapper;
import com.database.warehouse.mapper.ProductStorationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductMaterialMapper productMaterialMapper;
    @Autowired
    private ProductStorationMapper productStorationMapper;

    public List<Product> findProducts() {
        return productMapper.selectProducts();
    }

    public List<Product> findProductByWid(Long wid) {
        return productMapper.selectProductByWid(wid);
    }

    public Product findProductByPid(Long pid) {
        Product product = productMapper.selectProductByPid(pid);
        if (product == null) {
            throw new ProductNotFound();
        }
        return product;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Long addProduct(Product product) {
        Long pid = productMapper.selectValidPidByName(product.getName());
        if (pid != null) {
            throw new ProductExist();
        }
        pid = productMapper.selectPidByName(product.getName());
        if (pid != null) {
            product.setPid(pid);
            productMapper.activateProduct(product);
            return pid;
        } else {
            productMapper.insertProduct(product);
            return pid;
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProduct(Product product) throws MaterialExist {
        String name = product.getName();
        Long pid = productMapper.selectPidByName(name);
        if (pid != null) {
            throw new ProductExist();
        }
        productMapper.updateProduct(product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void removeProduct(Long pid) throws DeleteUnable {
        List<ProductStoration> productStorations =
                productStorationMapper.selectProductStorationByPid(pid);
        if (productStorations.size() != 0) {
            throw new DeleteUnable();
        }
        productMapper.deleteProduct(pid);
    }

}
