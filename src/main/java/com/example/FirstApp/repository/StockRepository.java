package com.example.FirstApp.repository;

import com.example.FirstApp.models.ERole;
import com.example.FirstApp.models.Role;
import com.example.FirstApp.models.Stocks;
import com.example.FirstApp.models.UsersStocks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends CrudRepository<Stocks, Integer> {
    Stocks findByName(String name);
    Iterable<Stocks> findAll();
    @Query("UPDATE Stocks \n" +
            "SET price = ROUND(price * (1 + (RAND() - 0.5)), 4)")
    void updatePrice();
}
