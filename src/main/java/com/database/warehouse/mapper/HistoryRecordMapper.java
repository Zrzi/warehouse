package com.database.warehouse.mapper;

import com.database.warehouse.entity.HistoryRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HistoryRecordMapper {

    Integer insertHistoryRecord(@Param("record") HistoryRecord historyRecord);

}
