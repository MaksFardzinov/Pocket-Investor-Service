package com.example.FirstApp.models;


import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.Collection;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="username",unique = true)
    private String username;
    @Column(name ="password" )
    private String password;
    @Column(name ="email",unique = true )
    private String email;
    @Column(name ="firstname" )
    private String firstname;
    @Column(name ="lastname" )
    private String lastname;
    @Column(name ="balance")
    private double balance;



    @ManyToMany()
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;
    @ManyToMany()
    @JoinTable(name = "users_stocks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id")
    )
    private Collection<Stocks> stocks;

    public User() {}
    public User( String username,String firstname, String lastname, String email, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUser_id() {
        return id;
    }

    public void setUser_id(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection roles) {
        this.roles = roles;
    }

    public Collection<Stocks> getStocks() {
        return stocks;
    }

    public void setStocks(Collection<Stocks> stocks) {
        this.stocks = stocks;
    }
}
