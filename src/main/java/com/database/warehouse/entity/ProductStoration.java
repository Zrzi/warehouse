package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductStoration {

    private Long id;
    private Long pid;
    private Long wid;
    private String time;
    private Integer number;
    private Integer restNumber;

    public String getTypeName() {
        return "Product_Storation";
    }

}
