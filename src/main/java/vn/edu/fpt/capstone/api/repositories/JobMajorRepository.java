package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.edu.fpt.capstone.api.entities.JobMajor;


import java.util.List;

public interface JobMajorRepository extends JpaRepository<JobMajor, Long>, JpaSpecificationExecutor<JobMajor> {

    List<JobMajor> findByJobId(Long jobId);

}
