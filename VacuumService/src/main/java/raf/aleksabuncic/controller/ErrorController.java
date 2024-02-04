package raf.aleksabuncic.controller;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.aleksabuncic.dto.ErrorDto;
import raf.aleksabuncic.security.service.TokenService;
import raf.aleksabuncic.service.ErrorService;

import java.util.List;

@RestController
@RequestMapping("/error")
@CrossOrigin(origins = "http://localhost:4200")
public class ErrorController {
    private final ErrorService errorService;
    private final TokenService tokenService;

    public ErrorController(ErrorService errorService, TokenService tokenService) {
        this.errorService = errorService;
        this.tokenService = tokenService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ErrorDto>> list(@RequestHeader("Authorization") String authorization) {
        Long userId = getUserId(authorization);
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(errorService.list(userId), HttpStatus.OK);
    }

    private Long getUserId(String authorization) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get("id", Long.class);
    }
}
