package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {

    private Long did;
    private String name;
    private Long eid;

    public String getTypeName() {
        return "Department";
    }

}
