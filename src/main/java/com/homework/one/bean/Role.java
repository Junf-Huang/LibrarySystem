package com.homework.one.bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //产生自增表
    private Integer roleId;

    //@ManyToOne(cascade= CascadeType.ALL)
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Role() {
        this.role = "USER";
    }
}
