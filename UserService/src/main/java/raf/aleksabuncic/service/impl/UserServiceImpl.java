package raf.aleksabuncic.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.User;
import raf.aleksabuncic.dto.CreateUserDto;
import raf.aleksabuncic.dto.TokenRequestDto;
import raf.aleksabuncic.dto.TokenResponseDto;
import raf.aleksabuncic.dto.UserDto;
import raf.aleksabuncic.exception.NotFoundException;
import raf.aleksabuncic.mapper.UserMapper;
import raf.aleksabuncic.repository.UserRepository;
import raf.aleksabuncic.security.service.TokenService;
import raf.aleksabuncic.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, TokenService tokenService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public UserDto register(CreateUserDto createUserDto) {
        Optional<User> users = userRepository.findUserByMailAndPassword(createUserDto.getMail(), createUserDto.getPassword());

        if (users.isEmpty()) {
            User user = userMapper.createUserDtoToUser(createUserDto);
            userRepository.save(user);
            return userMapper.userToUserDto(user);
        }
        return null;
    }

    @Override
    public List<UserDto> findUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(userMapper.userToUserDto(user));
        }
        return userDtos;
    }

    public UserDto findUser(int id) {
        User user = userRepository.findByUserId(id);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto addUser(CreateUserDto createUserDto) {
        User user = userMapper.createUserDtoToUser(createUserDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findByUserId(userDto.getUserId());

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getMail() != null) {
            user.setMail(userDto.getMail());
        }
        if (userDto.getPermissions() != null) {
            user.setPermissions(userDto.getPermissions());
        }

        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto deleteUser(int id) {
        User user = userRepository.findByUserId(id);
        userRepository.delete(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository.findUserByMailAndPassword(tokenRequestDto.getMail(), tokenRequestDto.getPassword()).orElseThrow(() -> new NotFoundException(String.format("User with username: %s and password: %s not found.", tokenRequestDto.getMail(), tokenRequestDto.getPassword())));

        Claims claims = Jwts.claims();
        claims.put("id", user.getUserId());
        claims.put("permissions", user.getPermissions());

        return new TokenResponseDto(tokenService.generate(claims));
    }
}
