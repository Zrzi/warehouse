package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Warehouse {

    private Long wid;
    private String type;
    private String address;
    private Integer max;
    private Integer min;

    public String getTypeName() {
        return "Warehouse";
    }

}
