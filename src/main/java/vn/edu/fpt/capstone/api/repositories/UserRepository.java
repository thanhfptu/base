package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEduEmail(String universityEmail);

    List<User> findByIdIn(List<Long> ids);

    Optional<User> findByRollNumber(String rollNumber);

}
