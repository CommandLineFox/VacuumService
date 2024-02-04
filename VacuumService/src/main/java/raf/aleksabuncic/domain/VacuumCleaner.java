package raf.aleksabuncic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "cleanerTable")
public class VacuumCleaner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cleanerId")
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
