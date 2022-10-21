package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.UserSemester;

@Repository
public interface UserSemesterRepository extends JpaRepository<UserSemester, Long> {
}
