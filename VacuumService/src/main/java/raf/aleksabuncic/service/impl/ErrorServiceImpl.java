package raf.aleksabuncic.service.impl;

import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.Error;
import raf.aleksabuncic.dto.ErrorDto;
import raf.aleksabuncic.mapper.ErrorMapper;
import raf.aleksabuncic.repository.ErrorRepository;
import raf.aleksabuncic.service.ErrorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorServiceImpl implements ErrorService {
    private final ErrorMapper errorMapper;
    private final ErrorRepository errorRepository;

    public ErrorServiceImpl(ErrorMapper errorMapper, ErrorRepository errorRepository) {
        this.errorMapper = errorMapper;
        this.errorRepository = errorRepository;
    }

    @Override
    public List<ErrorDto> list(long userId) {
        List<Error> errors = errorRepository.findErrorByUserId(userId);
        ArrayList<ErrorDto> errorDtos = new ArrayList<>();
        for (Error error : errors) {
            errorDtos.add(errorMapper.errorToErrorDto(error));
        }

        return errorDtos;
    }
}
