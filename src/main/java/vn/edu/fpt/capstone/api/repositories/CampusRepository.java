package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.Campus;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long>, JpaSpecificationExecutor<Campus> {

    Optional<Campus> findByEmail(String email);

    Optional<Campus> findByPhoneNumber(String phoneNumber);

    List<Campus> findByIdIn(List<Long> ids);

}
