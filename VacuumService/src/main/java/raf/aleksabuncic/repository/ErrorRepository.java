package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import raf.aleksabuncic.domain.Error;

import java.util.List;

public interface ErrorRepository extends JpaRepository<Error, Long> {
    List<Error> findErrorByUserId(long userId);
}
