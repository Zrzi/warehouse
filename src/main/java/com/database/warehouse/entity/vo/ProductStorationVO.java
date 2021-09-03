package com.database.warehouse.entity.vo;

import com.database.warehouse.entity.Product;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductStorationVO {

    private Long wid;
    private Long pid;
    private Product product;
    private Integer number;

}
