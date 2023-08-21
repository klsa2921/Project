package com.example.Employee.Management.Backend.User.controller;

import com.example.Employee.Management.Backend.User.db.UserCrudOpp;
import com.example.Employee.Management.Backend.User.model.User;
import com.example.Employee.Management.Backend.User.model.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserCrudOpp userCrudOpp;

    @PostMapping("/insertUser")
    public ResponseEntity<String> insertUserData(@RequestBody Map<String,Object> data){
        try {
            UserDb userDb=new UserDb();
            userDb.setId((Integer) data.get("id"));
            userDb.setName((String) data.get("name"));
            userDb.setGender((String) data.get("gender"));
            userDb.setUserName((String) data.get("user_name"));
            userDb.setPassword((String) data.get("password"));
            userCrudOpp.insertData(userDb.getId(), userDb.getName(), userDb.getGender(), userDb.getUserName(), userDb.getPassword());
            return ResponseEntity.ok("User data added successfully");
        } catch (SQLException | IOException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Map<String,Object>>> getAllUser(){
        try{
            List<Map<String,Object>> usersList=new ArrayList<>();
            ResultSet rs= userCrudOpp.getAllUsersData();
            while (rs.next()){
                Map<String,Object> user=new HashMap<>();
                user.put("id",rs.getInt("id"));
                user.put("name",rs.getString("name"));
                user.put("gender",rs.getString("gender"));
                user.put("role_name",rs.getInt("role_name"));
                usersList.add(user);
            }
            return ResponseEntity.ok(usersList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/assignRole/{id}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable(name = "id") int id,@RequestBody Map<String,Object> user){
        try{
            int numberOfUsersUpdate= userCrudOpp.assignRoleById(id, (String) user.get("role_name"));
            if(numberOfUsersUpdate==1)
                return ResponseEntity.ok("Assigned role successfully...........");
            else
                return ResponseEntity.ok("No user found by id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") int id){
        try{
            int numberOfUsersDeleted= userCrudOpp.deleteUserById(id);
            if(numberOfUsersDeleted==1)
                return ResponseEntity.ok("User deleted successfully...........");
            else
                return ResponseEntity.ok("No user found by id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/findUserByUserName/{username}")
    public ResponseEntity<Map<String,Object>> getUserByUserName(@PathVariable("username") String userName){
        try{
            Map<String,Object> user=new HashMap<>();
            ResultSet rs=userCrudOpp.getUserByUserName(userName);
            while (rs.next()){
                user.put("id",rs.getInt("id"));
                user.put("name",rs.getInt("name"));
                user.put("gender",rs.getInt("gender"));
                user.put("username",rs.getInt("username"));
                user.put("password",rs.getInt("password"));
                user.put("role_name",rs.getInt("role_name"));
            }
            return  ResponseEntity.ok(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
