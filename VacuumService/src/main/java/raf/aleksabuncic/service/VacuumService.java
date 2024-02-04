package raf.aleksabuncic.service;

import raf.aleksabuncic.dto.CreateVacuumCleanerDto;
import raf.aleksabuncic.dto.SearchDto;
import raf.aleksabuncic.dto.VacuumCleanerDto;

import java.util.List;

public interface VacuumService {
    List<VacuumCleanerDto> list(long userId);

    List<VacuumCleanerDto> search(long userId, SearchDto searchDto);

    VacuumCleanerDto add(long userId, CreateVacuumCleanerDto createVacuumCleanerDto);

    int rmeove(long id, long userId);

    int start(long id, long userId);

    int stop(long id, long userId);

    int discharge(long id, long userId);

    int scheduleStart(long id, long userId, long scheduledAction);

    int scheduleStop(long id, long userId, long scheduledAction);

    int scheduleDischarge(long id, long userId, long scheduledAction);
}
