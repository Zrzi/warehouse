package com.database.warehouse.entity.vo;

import com.database.warehouse.entity.Warehouse;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WarehouseVO {

    private Long wid;
    private String address;
    private Integer max;
    private Integer min;
    private Integer number;
    private String type;

    public WarehouseVO(Warehouse warehouse) {
        this.wid = warehouse.getWid();
        this.address = warehouse.getAddress();
        this.min = warehouse.getMin();
        this.max = warehouse.getMax();
        this.type = warehouse.getType();
    }

}
