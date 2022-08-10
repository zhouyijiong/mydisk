package com.zyj.disk.entity.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 用户实体类
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public final class User {
    private Integer id;
    private String username;
    private String password;
    private String path;
    private Long capacity;
    private Integer fileCount;
    private Long fileSize;
    private Long totalFileSize;
    private Integer authority;

    public static User noArgs() {
        return new User();
    }

    public static User defaultArgs() {
        return noArgs()
                .capacity(3221225472L)
                .fileCount(100)
                .fileSize(3221225472L)
                .totalFileSize(3221225472L)
                .authority(0);
    }

    public User id(Integer val) {
        id = val;
        return this;
    }

    public User username(String val) {
        username = val;
        return this;
    }

    public User password(String val) {
        password = val;
        return this;
    }

    public User path(String val) {
        path = val;
        return this;
    }

    public User capacity(Long val) {
        capacity = val;
        return this;
    }

    public User fileCount(Integer val) {
        fileCount = val;
        return this;
    }

    public User fileSize(Long val) {
        fileSize = val;
        return this;
    }

    public User totalFileSize(Long val) {
        totalFileSize = val;
        return this;
    }

    public User authority(Integer val) {
        authority = val;
        return this;
    }
}