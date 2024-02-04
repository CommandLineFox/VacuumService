package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private Integer permissions;
}
