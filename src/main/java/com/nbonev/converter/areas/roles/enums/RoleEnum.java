package com.nbonev.converter.areas.roles.enums;

public enum RoleEnum {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String roleName;

    private RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}