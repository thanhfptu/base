package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.Major;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {
    List<Major> findByIdIn(List<Long> majorIds);

    Major findByCode(String code);
}
