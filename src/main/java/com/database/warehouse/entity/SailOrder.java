package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SailOrder {

    private Long id;
    private Long eid;
    private Long pid;
    private String time;
    private Integer number;
    private Double price;

    public String getType() {
        return "SailOrder";
    }

}
