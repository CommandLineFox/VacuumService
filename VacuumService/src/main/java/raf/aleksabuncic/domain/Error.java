package raf.aleksabuncic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "errorTable")
public class Error {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "errorId")
    private Long errorId;
    private Long cleanerId;
    private Long userId;
    private Long date;
    private Integer status;
    private String message;
}
