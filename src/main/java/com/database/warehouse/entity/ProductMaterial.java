package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductMaterial {

    private Long pid;
    private Long mid;
    private Integer number;

    public String getType() {
        return "ProductMaterial";
    }

}
