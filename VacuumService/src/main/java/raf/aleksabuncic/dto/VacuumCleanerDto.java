package raf.aleksabuncic.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VacuumCleanerDto {
    private Long cleanerId;
    private String name;
    private Integer status;
    private Integer nextStatus;
    private Boolean active;
    private Long userId;
    private Long date;
    private Long scheduledAction;
    private Integer cycleCount;
}
