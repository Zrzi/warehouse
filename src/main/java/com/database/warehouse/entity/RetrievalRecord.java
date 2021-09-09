package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RetrievalRecord {

    private Long id;
    private Long mid;
    private Integer number;
    private Double price;
    private String time;

    public String getTypeName() {
        return "Retrieval Record";
    }

}
