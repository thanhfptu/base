package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.OJTInfo;

import java.util.List;

@Repository
public interface OJTInfoRepository extends JpaRepository<OJTInfo, Long>, JpaSpecificationExecutor<OJTInfo> {

    List<OJTInfo> findByIdIn(List<Long> ids);
}
