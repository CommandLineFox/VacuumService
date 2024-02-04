package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.CreateUserDto;
import raf.aleksabuncic.dto.TokenRequestDto;
import raf.aleksabuncic.dto.TokenResponseDto;
import raf.aleksabuncic.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findUser(int id);

    List<UserDto> findUsers();

    UserDto addUser(CreateUserDto createUserDto);

    UserDto updateUser(UserDto userDto);

    UserDto deleteUser(int id);

    UserDto register(CreateUserDto createUserDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}
