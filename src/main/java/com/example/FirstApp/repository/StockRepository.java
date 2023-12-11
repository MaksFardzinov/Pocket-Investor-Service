package com.example.FirstApp.repository;

import com.example.FirstApp.models.ERole;
import com.example.FirstApp.models.Role;
import com.example.FirstApp.models.Stocks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends CrudRepository<Stocks, Integer> {
    Stocks findByName(String name);
    Iterable<Stocks> findAll();
}
