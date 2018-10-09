package com.riyeyuedu.entity;

import lombok.*;

import java.util.List;

/**
 * @author haojie tang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserEntity {

    private Integer uid;

    private String username;

    private String password;

    private String phone;

    private Integer sex;

    private Long birthday;

    private String info;

    private String portrait;

    private Integer permission;

    private List<RoleEntity> roles;
}
