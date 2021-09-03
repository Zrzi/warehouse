package com.database.warehouse.mapper;

import com.database.warehouse.entity.PurchaseOrder;
import com.database.warehouse.entity.vo.PurchaseOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PurchaseOrderMapper {

    List<PurchaseOrderVO> selectUnapprovedOrderVO();

    List<PurchaseOrderVO> selectUnstoredOrderVO();

    PurchaseOrder selectPurchaseOrderById(@Param("id") Long id);

    List<PurchaseOrder> selectPurchaseOrderByEid(@Param("eid") Long eid);

    List<PurchaseOrder> selectPurchaseOrderByMid(@Param("mid") Long mid);

    List<PurchaseOrder> selectPurchaseOrder();

    List<PurchaseOrder> selectUnapprovedPurchaseOrder();

    List<PurchaseOrder> selectUnstoredPurchaseOrder();

    Integer insertPurchaseOrder(@Param("order") PurchaseOrder purchaseOrder);

    Integer updatePurchaseOrder(@Param("order") PurchaseOrder purchaseOrder);

    Integer deletePurchaseOrder(@Param("id") Long id);

}
