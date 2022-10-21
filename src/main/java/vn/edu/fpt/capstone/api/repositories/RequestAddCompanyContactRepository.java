package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.RequestAddCompanyContact;

import java.util.List;

@Repository
public interface RequestAddCompanyContactRepository extends JpaRepository<RequestAddCompanyContact, Long>, JpaSpecificationExecutor<RequestAddCompanyContact> {
    List<RequestAddCompanyContact> findByIdIn(List<Long> ids);
}
