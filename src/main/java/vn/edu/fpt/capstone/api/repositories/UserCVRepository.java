package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.edu.fpt.capstone.api.entities.UserCV;

import java.util.List;

public interface UserCVRepository extends JpaRepository<UserCV, Long>, JpaSpecificationExecutor<UserCV> {

    List<UserCV> findByStudentId(Long studentId);

    List<UserCV> findByIdIn(List<Long> id);
}
