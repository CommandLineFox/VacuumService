package raf.aleksabuncic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import raf.aleksabuncic.domain.VacuumCleaner;

import java.util.List;
import java.util.Optional;

public interface VacuumCleanerRepository extends JpaRepository<VacuumCleaner, Long> {
    @Query("SELECT v FROM VacuumCleaner v WHERE v.active = true AND v.userId = :userId AND v.cleanerId = :cleanerId")
    Optional<VacuumCleaner> findVacuumCleanerByCleanerId(@Param("cleanerId") Long cleanerIdg, @Param("userId") Long userId);

    @Query("SELECT v FROM VacuumCleaner v WHERE v.active = true AND v.userId = :userId AND (:name IS NULL OR v.name = :name) AND (:dateFrom IS NULL OR v.date >= :dateFrom) AND (:dateTo IS NULL OR v.date <= :dateTo) AND (:statusList IS NULL OR v.status IN :statusList)")
    List<VacuumCleaner> findVacuumCleanersByCriteria(@Param("userId") Long userId, @Param("name") String name, @Param("dateFrom") Long dateFrom, @Param("dateTo") Long dateTo, @Param("statusList") List<Integer> statusList);

    List<VacuumCleaner> findVacuumCleanersByScheduledActionIsNotNullAndActiveTrue();

    @Query("SELECT v FROM VacuumCleaner v WHERE v.active = true AND v.userId = :userId")
    List<VacuumCleaner> findVacuumCleanersByActive(@Param("userId") Long userId);
}
