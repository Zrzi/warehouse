package com.database.warehouse.service;

import com.database.warehouse.entity.*;
import com.database.warehouse.entity.vo.MaterialStorationVO;
import com.database.warehouse.entity.vo.ProductStorationVO;
import com.database.warehouse.exception.EmployeeNotFound;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.mapper.*;
import com.database.warehouse.utils.LocalTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StoreService {

    @Autowired
    private ProductStorationMapper productStorationMapper;
    @Autowired
    private MaterialStorationMapper materialStorationMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private CostMapper costMapper;
    @Autowired
    private SailOrderMapper sailOrderMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public Long storeProduct(Long pid, Long wid, Integer number, Double price)
            throws InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        Integer store = getRestProductNumber(wid);
        Integer max = warehouse.getMax();
        if (store + number > max) {
            throw new InvalidInput();
        }
        ProductStoration productStoration = new ProductStoration();
        productStoration.setPid(pid).setWid(wid).setTime(LocalTimeString.getLocalTimeNow())
                .setNumber(number).setRestNumber(number).setPrice(price);
        productStorationMapper.insertProductStoration(productStoration);
        return productStoration.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void storeMaterial(Long mid, Long wid, Integer number, Double price)
            throws InvalidInput {
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(wid);
        Integer store = getRestMaterialNumber(wid);
        Integer max = warehouse.getMax();
        if (store + number <= max) {
            MaterialStoration materialStoration =
                    new MaterialStoration(mid, wid, LocalTimeString.getLocalTimeNow(),
                            number, number, price);
            materialStorationMapper.insertMaterialStoration(materialStoration);
        } else {
            throw new InvalidInput();
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void removeProduct(String name, Long wid, Long pid, Integer number, Double price)
            throws EmployeeNotFound, InvalidInput {
        Long eid = employeeMapper.selectEidByName(name);
        if (eid == null) {
            throw new EmployeeNotFound();
        }
        Integer restNumber = productStorationMapper.selectSpecificRestNumber(wid, pid);
        if (restNumber < number) {
            throw new InvalidInput();
        }
        int sailNumber = number;
        List<ProductStoration> productStorations =
                productStorationMapper.selectProductStorationByKey(wid, pid);
        productStorations.sort((o1, o2) -> Integer.compare(o1.getTime().compareTo(o2.getTime()), 0));
        for (ProductStoration productStoration : productStorations) {
            Integer tempNumber = productStoration.getRestNumber();
            if (number >= tempNumber) {
                productStoration.setRestNumber(0);
                productStorationMapper.updateProductStoration(productStoration);
                number = number - tempNumber;
            } else {
                productStoration.setRestNumber(tempNumber - number);
                productStorationMapper.updateProductStoration(productStoration);
                number = 0;
                break;
            }
        }
        SailOrder sailOrder = new SailOrder();
        sailOrder.setEid(eid).setPid(pid).setNumber(sailNumber)
                .setPrice(price).setTime(LocalTimeString.getLocalTimeNow());
        sailOrderMapper.insertSailOrder(sailOrder);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void removeMaterial(Long mid, Long wid, Integer number, boolean fifo) {
        Integer restNumber = materialStorationMapper.selectSpecificRestNumber(wid, mid);
        if (restNumber < number) {
            throw new InvalidInput();
        }
        List<MaterialStoration> materialStorations =
                materialStorationMapper.selectMaterialStorationByKey(wid, mid);
        if (fifo) {
            materialStorations.sort((o1, o2) -> Integer.compare(o1.getTime().compareTo(o2.getTime()), 0));
        } else {
            materialStorations.sort((o1, o2) -> Integer.compare(0, o1.getTime().compareTo(o2.getTime())));
        }
        double price = 0.0;
        for (MaterialStoration materialStoration : materialStorations) {
            Integer tempNumber = materialStoration.getRestNumber();
            Double tempPrice = materialStoration.getPrice();
            if (number >= tempNumber) {
                price += tempNumber * tempPrice;
                materialStoration.setRestNumber(0);
                materialStorationMapper.updateMaterialStoration(materialStoration);
                number = number-tempNumber;
            } else {
                price += number * tempPrice;
                materialStoration.setRestNumber(tempNumber - number);
                materialStorationMapper.updateMaterialStoration(materialStoration);
                number = 0;
                break;
            }
        }
        String date = LocalTimeString.getCostString();
        Double cost = costMapper.selectCost(date);
        if (cost == null) {
            costMapper.insertCost(date, price);
        } else {
            costMapper.updateCost(date, cost + price);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void moveProduct(Long id, Long oldWid, Long newWid, Long pid, Integer number)
            throws InvalidInput{
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(oldWid);
        ProductStoration productStoration =
                productStorationMapper.selectProductStoration(id);
        if (getRestProductNumber(oldWid) - number < warehouse.getMin()
                || number > productStoration.getRestNumber()) {
            throw new InvalidInput();
        }
        warehouse = warehouseMapper.selectWarehouseByWid(newWid);
        if (getRestProductNumber(newWid) + number > warehouse.getMax()) {
            throw new InvalidInput();
        }
        ProductStoration newStoration = new ProductStoration();
        productStoration.setPid(pid).setWid(newWid).setTime(LocalTimeString.getLocalTimeNow())
                .setNumber(number).setRestNumber(number).setPrice(productStoration.getPrice());
        productStoration.setRestNumber(productStoration.getRestNumber() - number);
        productStorationMapper.updateProductStoration(productStoration);
        productStorationMapper.insertProductStoration(newStoration);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void moveMaterial(Long oldWid, Long newWid, Long mid, String time, Integer number)
            throws InvalidInput{
        Warehouse warehouse = warehouseMapper.selectWarehouseByWid(oldWid);
        MaterialStoration materialStoration =
                materialStorationMapper.selectMaterialStoration(oldWid, mid, time);
        if (getRestMaterialNumber(oldWid) - number < warehouse.getMin()
                || number > materialStoration.getRestNumber()) {
            throw new InvalidInput();
        }
        warehouse = warehouseMapper.selectWarehouseByWid(newWid);
        if (getRestMaterialNumber(newWid) + number > warehouse.getMax()) {
            throw new InvalidInput();
        }
        MaterialStoration newStoration =
                new MaterialStoration(newWid, mid, LocalTimeString.getLocalTimeNow(),
                        number, number, materialStoration.getPrice());
        materialStoration.setRestNumber(materialStoration.getRestNumber() - number);
        materialStorationMapper.updateMaterialStoration(materialStoration);
        materialStorationMapper.insertMaterialStoration(newStoration);
    }

    private int getRestSpecificProductNumber(Long wid, Long pid) {
        List<ProductStoration> productStorations =
                productStorationMapper.selectProductStorationByKey(wid, pid);
        int number = 0;
        for (ProductStoration productStoration : productStorations) {
            number += productStoration.getRestNumber();
        }
        return number;
    }

    private int getRestProductNumber(Long wid) {
        List<ProductStoration> productStorations =
                productStorationMapper.selectProductStorationByWid(wid);
        int number = 0;
        for (ProductStoration productStoration : productStorations) {
            number += productStoration.getRestNumber();
        }
        return number;
    }

    private int getRestSpecificMaterialNumber(Long wid, Long mid) {
        List<MaterialStoration> materialStorations =
                materialStorationMapper.selectMaterialStorationByKey(wid, mid);
        int number = 0;
        for (MaterialStoration materialStoration : materialStorations) {
            number += materialStoration.getRestNumber();
        }
        return number;
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

    @Transactional(rollbackFor = RuntimeException.class)
    public List<MaterialStorationVO> getMaterialStorations() {
        List<Warehouse> warehouses = warehouseMapper.selectWarehouseByType("原材料仓库");
        List<MaterialStorationVO> result = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            Long wid = warehouse.getWid();
            List<MaterialStorationVO> voList = materialStorationMapper.selectMaterialStorationVO(wid);
            result.addAll(voList);
        }
        return result;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public List<ProductStorationVO> getProductStorations() {
        List<Warehouse> warehouses = warehouseMapper.selectWarehouseByType("产品仓库");
        List<ProductStorationVO> result = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            Long wid = warehouse.getWid();
            List<ProductStorationVO> voList = productStorationMapper.selectProductStorationVO(wid);
            result.addAll(voList);
        }
        return result;
    }

}
