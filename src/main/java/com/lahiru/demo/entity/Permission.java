package com.lahiru.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "T_PERMISSION")
@Setter
@Getter
@ToString
public class Permission {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    
    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

}
