package com.graduate.training.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")


@NamedQueries(
        {
                @NamedQuery(name="user.getAll",
                        query="select u from User as u",
                        hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
        })


public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name") private String name;
    @Column(name="username") private String username;
    @Column(name="password") private String password;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
