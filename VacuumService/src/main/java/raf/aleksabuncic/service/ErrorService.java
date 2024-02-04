package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.ErrorDto;

import java.util.List;

public interface ErrorService {
    List<ErrorDto> list(long userId);
}
