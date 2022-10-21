package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.StudentApplyJob;

import java.util.List;

@Repository
public interface StudentApplyJobRepository extends JpaRepository<StudentApplyJob, Long>, JpaSpecificationExecutor<StudentApplyJob> {

    StudentApplyJob findByJobIdAndCvId(Long jobId, Long cvId);

    List<StudentApplyJob> findByCreatedBy(Long createBy);

    StudentApplyJob findByJobIdAndCreatedBy(Long jobId, Long operatorId);
}


