package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Material {

    private Long mid;
    private String name;

    public String getTypeName() {
        return "Material";
    }

}
