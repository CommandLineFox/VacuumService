package raf.aleksabuncic.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.constant.ErrorMessage;
import raf.aleksabuncic.constant.Result;
import raf.aleksabuncic.constant.Status;
import raf.aleksabuncic.domain.Error;
import raf.aleksabuncic.domain.VacuumCleaner;
import raf.aleksabuncic.dto.CreateVacuumCleanerDto;
import raf.aleksabuncic.dto.SearchDto;
import raf.aleksabuncic.dto.VacuumCleanerDto;
import raf.aleksabuncic.mapper.VacuumCleanerMapper;
import raf.aleksabuncic.repository.ErrorRepository;
import raf.aleksabuncic.repository.VacuumCleanerRepository;
import raf.aleksabuncic.service.VacuumService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VacuumServiceImpl implements VacuumService {
    private final VacuumCleanerMapper vacuumCleanerMapper;
    private final VacuumCleanerRepository vacuumCleanerRepository;
    private final ErrorRepository errorRepository;
    private final ExecutorService executorService;

    public VacuumServiceImpl(VacuumCleanerMapper vacuumCleanerMapper, VacuumCleanerRepository vacuumCleanerRepository, ErrorRepository errorRepository) {
        this.vacuumCleanerMapper = vacuumCleanerMapper;
        this.vacuumCleanerRepository = vacuumCleanerRepository;
        this.errorRepository = errorRepository;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public List<VacuumCleanerDto> list(long userId) {
        List<VacuumCleaner> vacuumCleaners = vacuumCleanerRepository.findVacuumCleanersByActive(userId);
        ArrayList<VacuumCleanerDto> vacuumCleanerDtos = new ArrayList<>();
        for (VacuumCleaner vacuumCleaner : vacuumCleaners) {
            vacuumCleanerDtos.add(vacuumCleanerMapper.vacuumCleanerToVacuumCleanerDto(vacuumCleaner));
        }

        return vacuumCleanerDtos;
    }

    @Override
    public List<VacuumCleanerDto> search(long userId, SearchDto searchDto) {
        List<VacuumCleaner> vacuumCleaners = vacuumCleanerRepository.findVacuumCleanersByCriteria(userId, searchDto.getName(), searchDto.getDateFrom(), searchDto.getDateTo(), searchDto.getStatus());
        ArrayList<VacuumCleanerDto> vacuumCleanerDtos = new ArrayList<>();
        for (VacuumCleaner vacuumCleaner : vacuumCleaners) {
            vacuumCleanerDtos.add(vacuumCleanerMapper.vacuumCleanerToVacuumCleanerDto(vacuumCleaner));
        }

        return vacuumCleanerDtos;
    }

    @Override
    public VacuumCleanerDto add(long userId, CreateVacuumCleanerDto createVacuumCleanerDto) {
        VacuumCleaner vacuumCleaner = vacuumCleanerMapper.createVacuumCleanerDtoToVacuumCleaner(createVacuumCleanerDto);
        vacuumCleaner.setUserId(userId);
        vacuumCleanerRepository.save(vacuumCleaner);
        return vacuumCleanerMapper.vacuumCleanerToVacuumCleanerDto(vacuumCleaner);
    }

    @Override
    public int rmeove(long id, long userId) {
        Optional<VacuumCleaner> optionalVacuumCleaner = vacuumCleanerRepository.findVacuumCleanerByCleanerId(id, userId);
        if (!optionalVacuumCleaner.isPresent()) {
            return Result.notFound;
        }

        VacuumCleaner vacuumCleaner = optionalVacuumCleaner.get();
        vacuumCleaner.setActive(false);
        vacuumCleanerRepository.save(vacuumCleaner);

        return Result.success;
    }

    @Override
    public int start(long id, long userId) {
        Optional<VacuumCleaner> optionalVacuumCleaner = vacuumCleanerRepository.findVacuumCleanerByCleanerId(id, userId);
        if (!optionalVacuumCleaner.isPresent()) {
            generateError(id, userId, -1, ErrorMessage.notFound);
            return Result.notFound;
        }

        VacuumCleaner vacuumCleaner = optionalVacuumCleaner.get();
        if (vacuumCleaner.getStatus() != Status.vacuumStopped) {
            generateError(id, userId, vacuumCleaner.getStatus(), ErrorMessage.invalidState);
            return Result.badRequest;
        }
        if (vacuumCleaner.getNextStatus() != null && vacuumCleaner.getScheduledAction() == null) {
            generateError(id, userId, vacuumCleaner.getStatus(), ErrorMessage.nextOperationMissing);
            return Result.badRequest;
        }

        vacuumCleaner.setNextStatus(Status.vacuumRunning);
        executorService.submit(() -> {
            try {
                handle(vacuumCleaner);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        return Result.success;
    }

    @Override
    public int stop(long id, long userId) {
        Optional<VacuumCleaner> optionalVacuumCleaner = vacuumCleanerRepository.findVacuumCleanerByCleanerId(id, userId);
        if (!optionalVacuumCleaner.isPresent()) {
            generateError(id, userId, -1, ErrorMessage.notFound);
            return Result.notFound;
        }

        VacuumCleaner vacuumCleaner = optionalVacuumCleaner.get();
        if (vacuumCleaner.getStatus() == Status.vacuumStopped) {
            generateError(id, userId, 0, ErrorMessage.invalidState);
            return Result.badRequest;
        }
        if (vacuumCleaner.getNextStatus() == null && vacuumCleaner.getScheduledAction() == null) {
            generateError(id, userId, vacuumCleaner.getStatus(), ErrorMessage.nextOperationMissing);
            return Result.badRequest;
        }

        vacuumCleaner.setNextStatus(Status.vacuumStopped);
        executorService.submit(() -> {
            try {
                handle(vacuumCleaner);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        return Result.success;
    }

    @Override
    public int discharge(long id, long userId) {
        Optional<VacuumCleaner> optionalVacuumCleaner = vacuumCleanerRepository.findVacuumCleanerByCleanerId(id, userId);
        if (!optionalVacuumCleaner.isPresent()) {
            generateError(id, userId, -1, ErrorMessage.notFound);
            return Result.notFound;
        }

        VacuumCleaner vacuumCleaner = optionalVacuumCleaner.get();
        if (vacuumCleaner.getStatus() == Status.vacuumDischarging) {
            generateError(id, userId, vacuumCleaner.getStatus(), ErrorMessage.invalidState);
            return Result.badRequest;
        }
        if (vacuumCleaner.getNextStatus() != null && vacuumCleaner.getScheduledAction() == null) {
            generateError(id, userId, vacuumCleaner.getStatus(), ErrorMessage.nextOperationMissing);
            return Result.badRequest;
        }

        vacuumCleaner.setNextStatus(Status.vacuumDischarging);
        executorService.submit(() -> {
            try {
                handle(vacuumCleaner);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        vacuumCleaner.setCycleCount(0);
        return Result.success;
    }

    @Override
    public int scheduleStart(long id, long userId, long scheduledAction) {
        Optional<VacuumCleaner> optionalVacuumCleaner = vacuumCleanerRepository.findVacuumCleanerByCleanerId(id, userId);
        if (!optionalVacuumCleaner.isPresent()) {
            return Result.notFound;
        }

        VacuumCleaner vacuumCleaner = optionalVacuumCleaner.get();
        vacuumCleaner.setScheduledAction(scheduledAction);
        vacuumCleaner.setNextStatus(Status.vacuumRunning);
        vacuumCleanerRepository.save(vacuumCleaner);

        return Result.success;
    }

    @Override
    public int scheduleStop(long id, long userId, long scheduledAction) {
        Optional<VacuumCleaner> optionalVacuumCleaner = vacuumCleanerRepository.findVacuumCleanerByCleanerId(id, userId);
        if (!optionalVacuumCleaner.isPresent()) {
            return Result.notFound;
        }

        VacuumCleaner vacuumCleaner = optionalVacuumCleaner.get();
        vacuumCleaner.setScheduledAction(scheduledAction);
        vacuumCleaner.setNextStatus(Status.vacuumStopped);
        vacuumCleanerRepository.save(vacuumCleaner);

        return Result.success;
    }

    @Override
    public int scheduleDischarge(long id, long userId, long scheduledAction) {
        Optional<VacuumCleaner> optionalVacuumCleaner = vacuumCleanerRepository.findVacuumCleanerByCleanerId(id, userId);
        if (!optionalVacuumCleaner.isPresent()) {
            return Result.notFound;
        }

        VacuumCleaner vacuumCleaner = optionalVacuumCleaner.get();
        vacuumCleaner.setScheduledAction(scheduledAction);
        vacuumCleaner.setNextStatus(Status.vacuumDischarging);
        vacuumCleanerRepository.save(vacuumCleaner);

        return Result.success;
    }

    private void handle(VacuumCleaner vacuumCleaner) throws InterruptedException {
        Thread.sleep(15000);
        vacuumCleaner.setStatus(vacuumCleaner.getNextStatus());
        vacuumCleaner.setNextStatus(null);
        vacuumCleanerRepository.save(vacuumCleaner);

        if (vacuumCleaner.getStatus() == Status.vacuumStopped && vacuumCleaner.getCycleCount() == 3) {
            discharge(vacuumCleaner.getCleanerId(), vacuumCleaner.getUserId());
        }
        if (vacuumCleaner.getStatus() == Status.vacuumDischarging) {
            stop(vacuumCleaner.getCleanerId(), vacuumCleaner.getUserId());
        }
    }

    @Scheduled(initialDelay = 10000, fixedRate = 10000)
    public void taskScheduler() {
        List<VacuumCleaner> vacuumCleaners = vacuumCleanerRepository.findVacuumCleanersByScheduledActionIsNotNullAndActiveTrue();
        for (VacuumCleaner vacuumCleaner : vacuumCleaners) {
            if (vacuumCleaner.getScheduledAction() <= System.currentTimeMillis()) {
                switch (vacuumCleaner.getNextStatus()) {
                    case (Status.vacuumRunning): {
                        start(vacuumCleaner.getCleanerId(), vacuumCleaner.getUserId());
                        break;
                    }
                    case (Status.vacuumStopped): {
                        stop(vacuumCleaner.getCleanerId(), vacuumCleaner.getUserId());
                        break;
                    }
                    case (Status.vacuumDischarging): {
                        discharge(vacuumCleaner.getCleanerId(), vacuumCleaner.getUserId());
                        break;
                    }
                }

                vacuumCleaner.setScheduledAction(null);
                vacuumCleanerRepository.save(vacuumCleaner);
            }
        }
    }

    private void generateError(long cleanerId, long userId, int status, String message) {
        Error error = new Error();

        error.setDate(System.currentTimeMillis());
        error.setStatus(status);
        error.setUserId(userId);
        error.setCleanerId(cleanerId);
        error.setMessage(message);

        errorRepository.save(error);
    }
}
