package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductApplication {

    private Long id;
    private Long pid;
    private Integer number;
    private Integer stored;

    public String getType() {
        return "ProductApplication";
    }

}
