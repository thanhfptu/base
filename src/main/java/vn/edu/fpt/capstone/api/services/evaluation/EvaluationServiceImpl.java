package vn.edu.fpt.capstone.api.services.evaluation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.api.entities.CompanyContact;
import vn.edu.fpt.capstone.api.entities.Semester;
import vn.edu.fpt.capstone.api.entities.dto.EvaluationDTO;
import vn.edu.fpt.capstone.api.repositories.CompanyContactRepository;
import vn.edu.fpt.capstone.api.repositories.SemesterRepository;
import vn.edu.fpt.capstone.api.responses.BaseResponse;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

import static vn.edu.fpt.capstone.api.entities.dto.FinalEvaluationDTO.SQL_RESULT_SET_MAPPING;

@Slf4j
@RequiredArgsConstructor
@Service(EvaluationService.SERVICE_NAME)
public class EvaluationServiceImpl implements EvaluationService{

    private final EntityManager entityManager;

    private final SemesterRepository semesterRepository;

    private final CompanyContactRepository companyContactRepository;

    @Override
    public BaseResponse<List<EvaluationDTO>> getByContactId(Long contactId) {
        try {
            Semester currentSemester = semesterRepository.getCurrentSemester();

            if (Objects.isNull(currentSemester)) {
                throw new IllegalArgumentException("Đã xảy ra lỗi với dữ liệu");
            }

            CompanyContact contact = companyContactRepository.findById(contactId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy contact với id %s!", contactId)));

            StringBuilder selectBuild = new StringBuilder("SELECT ")
                    .append("eva.id as id, ")
                    .append("student.full_name as student_name, ")
                    .append("student.roll_number as roll_number, ")
                    .append("eva.midterm_evaluation_id as midterm_id, ")
                    .append("eva.midterm_evaluation_status as midterm_status, ")
                    .append("eva.final_evaluation_id as final_id, ")
                    .append("eva.final_evaluation_status as final_status ");

            StringBuilder fromBuild = new StringBuilder("FROM evaluation eva ")
                    .append("JOIN users student on eva.student_id = student.id ")
                    .append("LEFT JOIN midterm_evaluation midterm on midterm.id = eva.midterm_evaluation_id ")
                    .append("LEFT JOIN final_evaluation final on final.id = eva.final_evaluation_id ");

            StringBuilder whereBuild = new StringBuilder("WHERE ")
                    .append("eva.semester_id = :semesterId ")
                    .append("and eva.company_contact_id = :contactId ");

            String queryBuild = new StringBuilder()
                    .append(selectBuild)
                    .append(fromBuild)
                    .append(whereBuild)
                    .toString();

            Query query = entityManager.createNativeQuery(queryBuild, EvaluationDTO.SQL_RESULT_SET_MAPPING)
                    .setParameter("semesterId", currentSemester.getId())
                    .setParameter("contactId", contactId);

            List<EvaluationDTO> response = query.getResultList();

            return BaseResponse.success(response);

        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }

    }
}
