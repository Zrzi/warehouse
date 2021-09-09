package com.database.warehouse.mapper;

import com.database.warehouse.entity.RetrievalRecord;
import com.database.warehouse.entity.vo.RetrievalRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RetrievalRecordMapper {

    List<RetrievalRecordVO> selectRetrievalRecords(@Param("time") String time);

    List<RetrievalRecord> selectRestRetrievalReocrds(@Param("time") String time);

    Integer selectRestNumberByMid(@Param("mid") Long mid);

    List<RetrievalRecord> selectRetrievalRecordByMid(@Param("mid") Long mid);

    RetrievalRecord selectRetrievalRecordById(@Param("id") Long id);

    Integer insertRetrievalRecord(@Param("record") RetrievalRecord record);

    Integer updateRetrievalRecord(@Param("record") RetrievalRecord record);

    Integer deleteRetrievalRecord(@Param("id") Long id);

}
