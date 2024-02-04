package raf.aleksabuncic.dto;

import lombok.Getter;

@Getter
public class TokenRequestDto {
    private final String mail;
    private final String password;

    public TokenRequestDto(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
}
