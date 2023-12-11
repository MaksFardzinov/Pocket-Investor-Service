package com.example.FirstApp.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name="users_stocks")
public class UsersStocks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="stock_id")
    private Integer stock_id;

    @Column(name="purchase_price")
    private  double purchase_price;
    @Column(name ="count")
    private int count;
    public UsersStocks() {}
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer username) {
        this.user_id = username;
    }

    public Integer getStock_id() {
        return stock_id;
    }

    public void setStock_id(Integer stock_id) {
        this.stock_id = stock_id;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
