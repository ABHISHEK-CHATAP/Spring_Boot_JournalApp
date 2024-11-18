package com.JournalApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JournalApp.entity.User;
import com.JournalApp.repository.UserRepository;
import com.JournalApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

     @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> AllUsers(){
        return userService.getAllUsers();
    }

   

    // @PutMapping("/{username}")
    // public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
    //     User userInDb = userService.getUserByUserName(username);
    //     if(userInDb != null){
    //         userInDb.setUsername(user.getUsername());
    //         userInDb.setPassword(user.getPassword());
    //         userService.savedUser(userInDb);
    //     }
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

    // above code is pehle ka update user code ab after security jo user logged In hai wahi update karega kyuki ("/user") se start hone wale rotes ko authentication lagega
    // jo bhi user Authenticate hota hai, toh uski details SecurityContextHolder ke pass store ho jati hai ..,
    // toh wahi se user ki details nikalenge 
    // In below code

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticatedUser.getName();
        User userInDb = userService.getUserByUserName(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String username = authenticatedUser.getName();
        userRepository.deleteByUsername(username);        
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
