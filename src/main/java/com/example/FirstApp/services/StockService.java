package com.example.FirstApp.services;

import com.example.FirstApp.models.Stocks;
import com.example.FirstApp.models.User;
import com.example.FirstApp.models.UsersStocks;
import com.example.FirstApp.pojo.stocks.ActionForBuyRequest;
import com.example.FirstApp.pojo.stocks.StocksResponse;
import com.example.FirstApp.repository.StockRepository;
import com.example.FirstApp.repository.UserRepository;
import com.example.FirstApp.repository.UsersStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    final
    StockRepository repository;
    final
    UsersStocksRepository usersStocksRepository;
    final UserRepository userRepository;

    public StockService(StockRepository repository, UsersStocksRepository usersStocksRepository, UserRepository userRepository, UserService userService) {
        this.repository = repository;
        this.usersStocksRepository = usersStocksRepository;
        this.userRepository = userRepository;
    }


    public String addStock(@RequestBody ActionForBuyRequest actionRequest, Principal principal) {
        String[] strings = principal.getName().split(" ");
        Stocks stock = repository.findByName(actionRequest.getName());
        UsersStocks usersStocks = new UsersStocks();
        User user = userRepository.findByUsername(strings[0]).get();
        if(user.getBalance()>=stock.getPrice()){
            List<UsersStocks> usersStocks1 = usersStocksRepository.findUsersStocksByStock_id(stock.getId(),user.getUser_id());
                if (usersStocks1.size()>0) {
                    usersStocks= usersStocks1.get(0);
                    usersStocks.setCount(usersStocks.getCount()+1);
                    usersStocks.setPurchase_price(usersStocks.getPurchase_price()+stock.getPrice());
                    user.setBalance(user.getBalance() - stock.getPrice());
                } else {
                    usersStocks.setStock_id(stock.getId());
                    usersStocks.setUser_id(Integer.parseInt(strings[2]));
                    usersStocks.setPurchase_price(stock.getPrice());
                    usersStocks.setCount(1);
                    user.setBalance(user.getBalance() - stock.getPrice());
                }
            userRepository.save(user);
            usersStocksRepository.save(usersStocks);
            return "the promotion is purchased" ;
        }
        else return "insufficient funds on the balance sheet";
    }
    public String sellStock(@RequestBody ActionForBuyRequest actionRequest, Principal principal) {
        String[] strings = principal.getName().split(" ");
        Stocks stock = repository.findByName(actionRequest.getName());
        User user = userRepository.findByUsername(strings[0]).get();
        List<UsersStocks> usersStocks1 = usersStocksRepository.findUsersStocksByStock_id(stock.getId(),user.getUser_id());
        if (usersStocks1.size()>0) {
            UsersStocks usersStocks = usersStocks1.get(0);
            user.setBalance(user.getBalance() + (stock.getPrice() * usersStocks.getCount()));
            usersStocksRepository.delete(usersStocks);
            userRepository.save(user);
            return "ok";
        }
        else return "stocks have already been sold";
    }


    public List<StocksResponse> getAllActions(){
        List<Stocks> listActions =(List<Stocks>) repository.findAll() ;
        List<StocksResponse> actions = new ArrayList<>();
        for (Stocks listAction : listActions) {
            StocksResponse stocksResponse = new StocksResponse();
            stocksResponse.setName(listAction.getName());
            stocksResponse.setDescription(listAction.getDescription());
            stocksResponse.setPrice(listAction.getPrice());
            actions.add(stocksResponse);
        }
        return actions;
    }
    public void updatePrice(){
        repository.updatePrice();
    }

}
