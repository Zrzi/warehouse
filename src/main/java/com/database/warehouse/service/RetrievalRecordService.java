package com.database.warehouse.service;

import com.database.warehouse.entity.PurchaseOrder;
import com.database.warehouse.entity.RetrievalRecord;
import com.database.warehouse.entity.vo.RetrievalRecordVO;
import com.database.warehouse.exception.InvalidInput;
import com.database.warehouse.exception.UpdateUnable;
import com.database.warehouse.mapper.*;
import com.database.warehouse.utils.LocalTimeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class RetrievalRecordService {

    @Autowired
    private RetrievalRecordMapper retrievalRecordMapper;
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private CostMapper costMapper;

    public List<RetrievalRecordVO> getRetrievalRecords() {
        String time = LocalTimeString.getCostString();
        return retrievalRecordMapper.selectRetrievalRecords(time);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void returnMaterial(Long id, Integer number,Long eid) {
        RetrievalRecord record = retrievalRecordMapper.selectRetrievalRecordById(id);
        if (record == null || eid == null || record.getNumber() < number) {
            throw new InvalidInput();
        }
        String time = LocalTimeString.getCostString();
        String date = LocalTimeString.getLocalTimeNow();
        if (!time.equals(record.getTime())) {
            throw new InvalidInput();
        }
        int restNumber = record.getNumber() - number;
        BigDecimal cost = BigDecimal.valueOf(costMapper.selectCost(time));
        BigDecimal minus = BigDecimal.valueOf(record.getPrice()).multiply(BigDecimal.valueOf(number));
        BigDecimal result = cost.subtract(minus);
        costMapper.updateCost(time, result.doubleValue());
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setEid(eid).setMid(record.getMid()).setNumber(number).setRestNumber(number)
                .setPrice(record.getPrice()).setTime(date).setApproved(1).setStored(0);
        purchaseOrderMapper.insertUnstoredPurchaseOrder(purchaseOrder);
        if (restNumber == 0) {
            retrievalRecordMapper.deleteRetrievalRecord(id);
        } else {
            record.setNumber(restNumber);
            retrievalRecordMapper.updateRetrievalRecord(record);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void damageMaterial(Long id, Integer number) {
        RetrievalRecord record = retrievalRecordMapper.selectRetrievalRecordById(id);
        if (record == null || record.getNumber() < number) {
            throw new InvalidInput();
        }
        int restNumber = record.getNumber() - number;
        if (restNumber == 0) {
            retrievalRecordMapper.deleteRetrievalRecord(id);
        } else {
            record.setNumber(restNumber);
            retrievalRecordMapper.updateRetrievalRecord(record);
        }
    }

    public void deleteRetrievalRecord(Long id) {
        purchaseOrderMapper.deletePurchaseOrder(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateCost()
            throws UpdateUnable{
        String date = LocalTimeString.getCostString();
        if (costMapper.selectIsChecked(date) == 1) {
            throw new UpdateUnable();
        }
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        if (Period.between(today, lastDayOfMonth).getDays() != 0) {
            throw new UpdateUnable();
        }
        List<RetrievalRecord> retrievalRecords
                = retrievalRecordMapper.selectRestRetrievalReocrds(date);
        BigDecimal temp = BigDecimal.ZERO;
        String nextMonth = LocalTimeString.getNextMonth();
        for (RetrievalRecord retrievalRecord : retrievalRecords) {
            BigDecimal bigDecimal = BigDecimal.valueOf(retrievalRecord.getPrice());
            temp = temp.add(bigDecimal);
            retrievalRecord.setTime(nextMonth);
            retrievalRecordMapper.updateRetrievalRecord(retrievalRecord);
        }
        costMapper.insertCost(nextMonth, temp.doubleValue());
        costMapper.checkCost(date);
    }

}
