package com.max.springproject.security;

public enum UserPermission {
    FILE_READ("file:read"),
    FILE_WRITE("file:write"),
    FILE_DELETE("file:delete"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    EVENT_READ("event:read"),
    EVENT_WRITE("event:write"),
    EVENT_DELETE("event:delete");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
