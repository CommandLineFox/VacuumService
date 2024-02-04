package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private Integer permissions;

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
