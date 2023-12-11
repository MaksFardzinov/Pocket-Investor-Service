package com.example.FirstApp.repository;

import com.example.FirstApp.models.UsersStocks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersStocksRepository extends CrudRepository<UsersStocks,Integer>, CustomizedUserStocks<Object[]> {
    @Query("Select u from UsersStocks u where u.stock_id =:stock_id and u.user_id=:user_id")
    List<UsersStocks> findUsersStocksByStock_id(@Param("stock_id") Integer stockId,@Param("user_id") Integer userId);
    @Query("Select u from UsersStocks u where u.user_id =:user_id")
    List<UsersStocks> findUsersStocksByUser_id(@Param("user_id") Integer stockId);
}
