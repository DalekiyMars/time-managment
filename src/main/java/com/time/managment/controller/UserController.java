package com.time.managment.controller;

import com.time.managment.entity.User;
import com.time.managment.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.time.managment.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add-new")
    public UserDTO saveNewUser(@RequestBody @Valid User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Integer id){
        return userService.getUser(id);
    }

    @PostMapping("/update/{timesheet}")
    public UserDTO updateExistedUser(@PathVariable("timesheet") Integer timesheet, @RequestBody @Valid User user){
        return userService.updateUser(timesheet, user);
    }
}
