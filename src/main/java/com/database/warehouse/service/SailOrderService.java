package com.database.warehouse.service;

import com.database.warehouse.entity.HistoryRecord;
import com.database.warehouse.entity.ProductStoration;
import com.database.warehouse.entity.SailOrder;
import com.database.warehouse.exception.DeleteUnable;
import com.database.warehouse.mapper.HistoryRecordMapper;
import com.database.warehouse.mapper.ProductMapper;
import com.database.warehouse.mapper.ProductStorationMapper;
import com.database.warehouse.mapper.SailOrderMapper;
import com.database.warehouse.utils.LocalTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SailOrderService {

    @Autowired
    private SailOrderMapper sailOrderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductStorationMapper productStorationMapper;
    @Autowired
    private HistoryRecordMapper historyRecordMapper;

    public List<SailOrder> findSailOrder() {
        return sailOrderMapper.selectSailOrder();
    }

    public Long addSailOrder(SailOrder sailOrder) {
        sailOrder.setTime(LocalTimeString.getLocalTimeNow());
        sailOrderMapper.insertSailOrder(sailOrder);
        return sailOrder.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public SailOrder removeSailOrder(Long id) throws DeleteUnable {
        SailOrder sailOrder = sailOrderMapper.selectSailOrderByKey(id);
        String time = sailOrder.getTime();
        LocalDateTime localDateTime = LocalTimeString.toLocalDateTime(time);
        if (localDateTime.plusMonths(1).isBefore(LocalDateTime.now())) {
            throw new DeleteUnable();
        }
        sailOrderMapper.deleteSailOrder(id);
        return sailOrder;
    }

}
