package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.edu.fpt.capstone.api.entities.CompanyContact;

import java.util.List;
import java.util.Optional;

public interface CompanyContactRepository extends JpaRepository<CompanyContact, Long>, JpaSpecificationExecutor<CompanyContact> {

    List<CompanyContact> findByCompanyId(Long id);

    Optional<CompanyContact> findByEmail(String email);

}
