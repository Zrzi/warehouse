package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRole {

    private Long eid;
    private Long rid;

    public String getTypeName() {
        return "EmployeeRole";
    }

}
