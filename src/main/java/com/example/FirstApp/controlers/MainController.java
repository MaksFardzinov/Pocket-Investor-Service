package com.example.FirstApp.controlers;

import com.example.FirstApp.pojo.stocks.ActionForBuyRequest;
import com.example.FirstApp.pojo.stocks.StocksResponse;
import com.example.FirstApp.pojo.user.UsersActionForGetResponse;
import com.example.FirstApp.services.StockService;
import com.example.FirstApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
public class MainController {
    final
    StockService stockservice;
    final
    UserService userService;

    public MainController(StockService stockservice, UserService userService) {
        this.stockservice = stockservice;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> userData(Principal principal) {
      return userService.getInfo(principal);
    }
    @PostMapping("/addAction")
    public String addAction(@RequestBody ActionForBuyRequest actionRequest, Principal principal){
       return stockservice.addStock(actionRequest,principal);
    }
    @GetMapping("/getUserActions")
    public ResponseEntity<List<UsersActionForGetResponse>> getAllActions(Principal principal){

        return ResponseEntity.ok().body(userService.getAllActions(principal));
    }
    @PostMapping("/replenishBalance")
    public ResponseEntity<?>  replenishBalance(@RequestBody double money, Principal principal){
        return  userService.replenishBalance(money,principal);
    }
    @GetMapping("/getActions")
    public ResponseEntity<List<StocksResponse>> getActions(){

        return ResponseEntity.ok().body(stockservice.getAllActions());
    }
    @PostMapping("/sellAction")
    public String sellAction(@RequestBody ActionForBuyRequest actionRequest, Principal principal){
        return stockservice.sellStock(actionRequest,principal);
    }
    @PostMapping("/updatePrice")
    public  void  updatePrice(){
        stockservice.updatePrice();
    }
}