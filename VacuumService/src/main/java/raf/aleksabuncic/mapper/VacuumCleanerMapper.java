package raf.aleksabuncic.mapper;

import org.springframework.stereotype.Component;
import raf.aleksabuncic.domain.VacuumCleaner;
import raf.aleksabuncic.dto.CreateVacuumCleanerDto;
import raf.aleksabuncic.dto.VacuumCleanerDto;

@Component
public class VacuumCleanerMapper {
    public VacuumCleanerDto vacuumCleanerToVacuumCleanerDto(VacuumCleaner vacuumCleaner) {
        VacuumCleanerDto vacuumCleanerDto = new VacuumCleanerDto();
        vacuumCleanerDto.setCleanerId(vacuumCleaner.getCleanerId());
        vacuumCleanerDto.setName(vacuumCleaner.getName());
        vacuumCleanerDto.setStatus(vacuumCleaner.getStatus());
        vacuumCleanerDto.setNextStatus(vacuumCleaner.getNextStatus());
        vacuumCleanerDto.setActive(vacuumCleaner.getActive());
        vacuumCleanerDto.setUserId(vacuumCleaner.getUserId());
        vacuumCleanerDto.setDate(vacuumCleaner.getDate());
        vacuumCleanerDto.setCycleCount(vacuumCleaner.getCycleCount());

        return vacuumCleanerDto;
    }

    public VacuumCleaner createVacuumCleanerDtoToVacuumCleaner(CreateVacuumCleanerDto createVacuumCleanerDto) {
        VacuumCleaner vacuumCleaner = new VacuumCleaner();

        vacuumCleaner.setName(createVacuumCleanerDto.getName());
        vacuumCleaner.setStatus(0);
        vacuumCleaner.setActive(true);
        vacuumCleaner.setDate(createVacuumCleanerDto.getDate());
        vacuumCleaner.setCycleCount(0);

        return vacuumCleaner;
    }
}
