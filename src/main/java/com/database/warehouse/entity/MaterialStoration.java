package com.database.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MaterialStoration {

    private Long wid;
    private Long mid;
    private String time;
    private Integer number;
    private Integer restNumber;
    private Double price;

    public String getTypeName() {
        return "Material_Storation";
    }

}
