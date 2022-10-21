package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.FinalEvaluation;

@Repository
public interface FinalEvaluationRepository extends JpaRepository<FinalEvaluation, Long>, JpaSpecificationExecutor<FinalEvaluation> {
    FinalEvaluation findByStaffCode(String staffCode);
}
