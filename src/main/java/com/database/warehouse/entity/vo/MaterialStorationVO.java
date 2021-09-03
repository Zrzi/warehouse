package com.database.warehouse.entity.vo;

import com.database.warehouse.entity.Material;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MaterialStorationVO {

    private Long wid;
    private Long mid;
    private Material material;
    private Integer number;

}
