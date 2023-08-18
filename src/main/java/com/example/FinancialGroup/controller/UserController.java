package com.example.FinancialGroup.controller;

import com.example.FinancialGroup.dto.ApiResponseDto;
import com.example.FinancialGroup.dto.LoginRequestDto;
import com.example.FinancialGroup.dto.UserDto;
import com.example.FinancialGroup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponseDto newUser(@RequestBody UserDto userDto){
        return this.userService.saveUser(userDto);
    }

    @GetMapping
    public ApiResponseDto getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping(path = "/{userid}")
    public ApiResponseDto getUserById(@PathVariable Long userid){
        return this.userService.getUserById(userid);
    }

    @GetMapping(path = "/getusergroups/{userid}")
    public ApiResponseDto getUserGroups(@PathVariable Long userid){
        return this.userService.getUserGroups(userid);
    }

    @DeleteMapping(path = "/{userid}")
    public ApiResponseDto deleteUser(@PathVariable Long userid){return this.userService.deleteUser(userid);}

    @GetMapping("/login")
    public ApiResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return this.userService.login(loginRequestDto);
    }
}
