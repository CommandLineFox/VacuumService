package raf.aleksabuncic.dto;

import lombok.Getter;

@Getter
public class TokenResponseDto {
    private final String Token;

    public TokenResponseDto(String token) {
        Token = token;
    }
}
