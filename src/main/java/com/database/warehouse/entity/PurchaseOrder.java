package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseOrder {

    private Long id;
    private Long eid;
    private Long mid;
    private String time;
    private Integer number;
    private Integer restNumber;
    private Double price;
    private Integer approved;
    private Integer stored;

    public String getType() {
        return "PurchaseOrder";
    }

}
