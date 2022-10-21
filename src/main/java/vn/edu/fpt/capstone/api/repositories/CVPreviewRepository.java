package vn.edu.fpt.capstone.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.capstone.api.entities.CVPreview;

import java.util.List;

@Repository
public interface CVPreviewRepository extends JpaRepository<CVPreview, Long> {
    List<CVPreview> findByCvId(Long cvId);
    
}
