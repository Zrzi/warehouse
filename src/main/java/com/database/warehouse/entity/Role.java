package com.database.warehouse.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements GrantedAuthority {

    private Long rid;
    private String name;

    public String getTypeName() {
        return "Role";
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }else {
            return object instanceof Role && this.name.equals(((Role) object).name);
        }
    }

}
