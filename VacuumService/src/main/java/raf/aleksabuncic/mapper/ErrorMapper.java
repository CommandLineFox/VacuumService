package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.Error;
import raf.aleksabuncic.dto.ErrorDto;

@Component
public class ErrorMapper {
    public ErrorDto errorToErrorDto(Error error) {
        ErrorDto errorDto = new ErrorDto();

        errorDto.setErrorId(error.getErrorId());
        errorDto.setStatus(error.getStatus());
        errorDto.setDate(error.getDate());
        errorDto.setCleanerId(error.getCleanerId());
        errorDto.setUserId(error.getUserId());
        errorDto.setMessage(error.getMessage());

        return errorDto;
    }
}
