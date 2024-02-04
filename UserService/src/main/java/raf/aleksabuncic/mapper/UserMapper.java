package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.User;
import raf.aleksabuncic.dto.CreateUserDto;
import raf.aleksabuncic.dto.UserDto;

@Component
public class UserMapper {
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMail(user.getMail());
        userDto.setPassword(user.getPassword());
        userDto.setPermissions(user.getPermissions());

        return userDto;
    }

    public User createUserDtoToUser(CreateUserDto createUserDto) {
        User user = new User();

        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setMail(createUserDto.getMail());
        user.setPassword(createUserDto.getPassword());
        user.setPermissions(createUserDto.getPermissions());

        return user;
    }
}
