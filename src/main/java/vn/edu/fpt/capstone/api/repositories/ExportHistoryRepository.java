package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.ExportHistory;

@Repository
public interface ExportHistoryRepository extends JpaRepository<ExportHistory, Long>, JpaSpecificationExecutor<ExportHistory> {
}
