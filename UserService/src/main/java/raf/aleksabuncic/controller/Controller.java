package raf.aleksabuncic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.constant.Permissions;
import raf.aleksabuncic.dto.CreateUserDto;
import raf.aleksabuncic.dto.TokenRequestDto;
import raf.aleksabuncic.dto.TokenResponseDto;
import raf.aleksabuncic.dto.UserDto;
import raf.aleksabuncic.security.CheckSecurity;
import raf.aleksabuncic.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {
    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    @CheckSecurity(roles = Permissions.readUserPermission)
    public ResponseEntity<UserDto> findUser(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    @CheckSecurity(roles = Permissions.readUserPermission)
    public ResponseEntity<List<UserDto>> listUsers(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    @CheckSecurity(roles = Permissions.createUserPermission)
    public ResponseEntity<UserDto> addUser(@RequestHeader("Authorization") String authorization, @RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(userService.addUser(createUserDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        return new ResponseEntity<>(userService.register(createUserDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @PostMapping("/update")
    @CheckSecurity(roles = Permissions.updateUserPermission)
    public ResponseEntity<UserDto> updateUser(@RequestHeader("Authorization") String authorization, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @CheckSecurity(roles = Permissions.deleteUserPermission)
    public ResponseEntity<UserDto> banUser(@RequestHeader("Authorization") String authorization, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
}
