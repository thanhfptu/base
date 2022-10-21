package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.MidtermEvaluation;

import java.util.Optional;

@Repository
public interface MidtermEvaluationRepository extends JpaRepository<MidtermEvaluation, Long>, JpaSpecificationExecutor<MidtermEvaluation> {
    Optional<MidtermEvaluation> findByStaffCode(String staffCode);
}
