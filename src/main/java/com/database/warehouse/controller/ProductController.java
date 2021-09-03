package com.database.warehouse.controller;

import com.database.warehouse.entity.Material;
import com.database.warehouse.entity.Product;
import com.database.warehouse.entity.ResponseData;
import com.database.warehouse.exception.*;
import com.database.warehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @GetMapping("/products")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getProducts() {
        List<Product> products = productService.findProducts();
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("products", products);
        responseData.getData().put("size", products.size());
        return responseData;
    }

    @ResponseBody
    @GetMapping("/productByWid")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getProductByWid(@RequestParam("wid") Long wid) {
        List<Product> products = productService.findProductByWid(wid);
        ResponseData responseData = ResponseData.success();
        responseData.getData().put("products", products);
        responseData.getData().put("size", products.size());
        return responseData;
    }

    @ResponseBody
    @GetMapping("/product")
    @PreAuthorize("hasAnyRole('ROLE_WAREHOUSE_ADMIN', 'ROLE_PURCHASE_ORDER_DEPT', 'ROLE_PURCHASE_ORDER_ADMIN', 'ROLE_PRODUCTION_DEPT', 'ROLE_SAILS_DEPT', 'ROLE_FINANCE_DEPT')")
    public ResponseData getProductByPid(@RequestParam("pid") Long pid) {
        try {
            Product product = productService.findProductByPid(pid);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("product", product);
            return responseData;
        } catch (ProductNotFound e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/product")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData addProduct(@RequestParam("name") String name) {
        try {
            if ("".equals(name.trim())) {
                throw new InvalidInput();
            }
            Product product = new Product();
            product.setName(name);
            Long pid = productService.addProduct(product);
            ResponseData responseData = ResponseData.success();
            responseData.getData().put("pid", pid);
            return responseData;
        } catch (ProductExist | InvalidInput e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/product")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData updateProduct(@RequestParam("pid") Long pid,
                                      @RequestParam("name") String name) {
        try {
            if (pid == null || "".equals(name.trim())) {
                throw new InvalidInput();
            }
            Product product = new Product(pid, name);
            productService.updateProduct(product);
            return ResponseData.success();
        } catch (InvalidInput | ProductExist e) {
            return ResponseData.fail(e.getMessage());
        }
    }

    @ResponseBody
    @DeleteMapping("/product")
    @PreAuthorize("hasRole('ROLE_PRODUCTION_DEPT')")
    public ResponseData deleteProduct(@RequestParam("pid") Long pid) {
        try {
            productService.removeProduct(pid);
            return ResponseData.success();
        } catch (DeleteUnable e) {
            return ResponseData.fail(e.getMessage());
        }
    }

}
