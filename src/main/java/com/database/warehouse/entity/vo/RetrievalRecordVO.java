package com.database.warehouse.entity.vo;

import com.database.warehouse.entity.Material;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RetrievalRecordVO {

    private Long id;
    private Material material;
    private Integer number;
    private Double price;
    private String time;

}
