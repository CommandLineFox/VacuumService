package raf.aleksabuncic.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "UserTable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Integer userId;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    @Setter
    private String mail;
    @Setter
    private String password;
    @Setter
    private Integer permissions;
}
