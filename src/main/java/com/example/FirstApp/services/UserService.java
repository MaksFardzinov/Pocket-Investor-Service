package com.example.FirstApp.services;

import com.example.FirstApp.models.Stocks;
import com.example.FirstApp.models.User;
import com.example.FirstApp.models.UsersStocks;
import com.example.FirstApp.pojo.stocks.ActionForBuyRequest;
import com.example.FirstApp.pojo.user.UsersActionForGetResponse;
import com.example.FirstApp.pojo.user.RegistrationUser;
import com.example.FirstApp.repository.UserRepository;
import com.example.FirstApp.repository.UsersStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.*;

@Service
public class UserService {
    final
    PasswordEncoder passwordEncoder;
    final
    RoleService roleService;
    final
    UserRepository repository;
    final
    UsersStocksRepository usersStocksRepository;

    public UserService(PasswordEncoder passwordEncoder, RoleService roleService, UserRepository repository, UsersStocksRepository usersStocksRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.repository = repository;
        this.usersStocksRepository = usersStocksRepository;
    }

    public User createNewUser(RegistrationUser registrationUser) {
        User user = new User();
        user.setUsername(registrationUser.getUsername());
        user.setEmail(registrationUser.getEmail());
        user.setFirstname(registrationUser.getFirstname());
        user.setLastname(registrationUser.getLastname());
        user.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return repository.save(user);
    }
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
    public List<UsersActionForGetResponse> getAllActions(Principal principal){
        String[] strings = principal.getName().split(" ");
        User user = repository.findByUsername(strings[0]).get();
        List<UsersActionForGetResponse> actions = new ArrayList<>();
        List<Stocks>  listActions = (List<Stocks>) user.getStocks();
        for (Stocks listAction : listActions) {
            UsersActionForGetResponse actionForGetResponse = new UsersActionForGetResponse();
            List<UsersStocks>  usersStocks= usersStocksRepository.findUsersStocksByStock_id(listAction.getId(), user.getUser_id());
            actionForGetResponse.setName(listAction.getName());
            actionForGetResponse.setPrice(listAction.getPrice());
            actionForGetResponse.setCount(usersStocks.get(0).getCount());
            actions.add(actionForGetResponse);
        }
        return actions;
    }

    public ResponseEntity<?> replenishBalance(@RequestBody  Double money,Principal principal){
        String[] strings = principal.getName().split(" ");
        User user = repository.findByUsername(strings[0]).get();
        double balance = user.getBalance();
        balance = balance+money;
        user.setBalance(balance);
        repository.save(user);
        return ResponseEntity.ok("ok");
    }
    public ResponseEntity<Map<String, Object>> getInfo(Principal principal){
        String[] strings = principal.getName().split(" ");
        User user = repository.findByUsername(strings[0]).get();
        Map<String,Object> answer = new HashMap<>();
        answer.put("username",user.getUsername());
        answer.put("email",user.getEmail());
        answer.put("balance",user.getBalance());
        return ResponseEntity.ok(answer);
    }
}
