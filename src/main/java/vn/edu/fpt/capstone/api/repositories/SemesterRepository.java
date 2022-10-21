package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.Semester;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>, JpaSpecificationExecutor<Semester> {

    @Query("select s from Semester s where s.id = (select max(s1.id) from Semester s1 where s1.isActive = true)")
    Semester getCurrentSemester();

    Optional<Semester> findByIsActive(Boolean isActive);

    List<Semester> findByIdIn(List<Long> ids);

}
