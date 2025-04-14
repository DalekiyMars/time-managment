package com.time.managment.restController;

import com.time.managment.entity.User;
import com.time.managment.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.time.managment.service.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping("/add-new")
    public UserDTO saveNewUser(@RequestBody @Valid User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Integer id){
        return userService.getUserDTO(id);
    }

    @PostMapping("/update/{timesheet}")
    public UserDTO updateExistedUser(@PathVariable("timesheet") Integer timesheet, @RequestBody @Valid User user){
        return userService.updateUser(timesheet, user);
    }

    @DeleteMapping("/{timeSheet}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer timeSheet) {
        try {
            userService.deleteUser(timeSheet);
            return ResponseEntity.ok("User deleted successfully");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
