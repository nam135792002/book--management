package vn.edu.likelion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.likelion.model.user.UserRequest;
import vn.edu.likelion.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequest userRequest){
        return new ResponseEntity<>(userService.register(userRequest), HttpStatus.CREATED);
    }
}
