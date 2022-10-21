package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.ImportHistory;

@Repository
public interface ImportHistoryRepository extends JpaRepository<ImportHistory, Long>, JpaSpecificationExecutor<ImportHistory> {
}
