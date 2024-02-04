package raf.aleksabuncic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VacuumServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VacuumServiceApplication.class, args);
    }
}