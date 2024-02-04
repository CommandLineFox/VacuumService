package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
    private Long errorId;
    private Long cleanerId;
    private Long userId;
    private Long date;
    private Integer status;
    private String message;
}
