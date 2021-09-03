package com.database.warehouse.entity.vo;

import com.database.warehouse.entity.Employee;
import com.database.warehouse.entity.Material;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseOrderVO {

    private Long id;
    private String time;
    private Integer number;
    private Integer restNumber;
    private Double price;
    private Employee employee;
    private Material material;

}
