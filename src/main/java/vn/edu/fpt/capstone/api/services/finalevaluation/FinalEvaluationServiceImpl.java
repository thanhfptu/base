package vn.edu.fpt.capstone.api.services.finalevaluation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.entities.FinalEvaluation;
import vn.edu.fpt.capstone.api.entities.dto.FinalEvaluationDTO;
import vn.edu.fpt.capstone.api.repositories.FinalEvaluationRepository;
import vn.edu.fpt.capstone.api.requests.FinalEvaluationRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.FinalEvaluationResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static vn.edu.fpt.capstone.api.entities.dto.FinalEvaluationDTO.SQL_RESULT_SET_MAPPING;

@Slf4j
@RequiredArgsConstructor
@Service(FinalEvaluationService.SERVICE_NAME)
public class FinalEvaluationServiceImpl implements FinalEvaluationService {

    private final EntityManager entityManager;

    private final FinalEvaluationRepository finalEvaluationRepository;

    @Override
    public PageResponse<FinalEvaluationDTO> list(Pageable pageable, String studentName, String rollNumber, Long companyId, Long semesterId, String companyName, Date startDate, Date endDate, String staffCode, Long companyContactId, String contactName, Integer status) {
        StringBuilder selectBuild = new StringBuilder("SELECT ")
                .append("final.id as id, ")
                .append("semester.name as semester, ")
                .append("company.name as company_name, ")
                .append("final.start_date as start_date, ")
                .append("final.end_date as end_date, ")
                .append("final.duration as duration, ")
                .append("student.roll_number as roll_number, ")
                .append("final.staff_code as staff_code, ")
                .append("student.full_name as full_name, ")
                .append("final.division as division, ")
                .append("contact.name as contact_name, ")
                .append("final.comment as comment, ")
                .append("final.allowance as allowance, ")
                .append("final.major_point as major_point, ")
                .append("final.attitude_point as attitude_point, ")
                .append("final.soft_skill_point as soft_skill_point, ")
                .append("final.final_point as final_point, ")
                .append("eva.final_evaluation_status as status ");

        StringBuilder fromBuild = new StringBuilder("FROM ")
                .append("final_evaluation final ")
                .append("RIGHT JOIN evaluation eva on eva.final_evaluation_id = final.id ")
                .append("JOIN users student ON eva.student_id = student.id ")
                .append("JOIN company_contacts contact ON eva.company_contact_id = contact.id ")
                .append("JOIN companies company on company.id = contact.company_id ")
                .append("JOIN semesters semester on semester.id = eva.semester_id ");

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();

        if (StringUtils.hasText(studentName)) {
            whereBuild.append("AND student.full_name like CONCAT('%',:studentName,'%') ");
            params.put("studentName", studentName);
        }

        if (StringUtils.hasText(companyName)) {
            whereBuild.append("AND company.name like CONCAT('%',:companyName,'%') ");
            params.put("companyName", companyName);
        }

        if (StringUtils.hasText(staffCode)) {
            whereBuild.append("AND final.staff_code like CONCAT('%',:staffCode,'%') ");
            params.put("staffCode", staffCode);
        }

        if (StringUtils.hasText(rollNumber)) {
            whereBuild.append("AND student.roll_number like CONCAT('%',:rollNumber,'%') ");
            params.put("rollNumber", rollNumber);
        }

        if (StringUtils.hasText(contactName)) {
            whereBuild.append("AND contact.name like CONCAT('%',:contactName,'%') ");
            params.put("contactName", contactName);
        }

        if (Objects.nonNull(startDate)) {
            whereBuild.append("AND final.start_date = :startDate ");
            params.put("startDate", startDate);
        }

        if (Objects.nonNull(endDate)) {
            whereBuild.append("AND final.end_date = :endDate ");
            params.put("endDate", endDate);
        }

        if (Objects.nonNull(companyId)) {
            whereBuild.append("AND company.id = :companyId ");
            params.put("companyId", companyId);
        }

        if (Objects.nonNull(companyContactId)) {
            whereBuild.append("AND eva.company_contact_id = :companyContactId ");
            params.put("companyContactId", companyContactId);
        }

        if (Objects.nonNull(semesterId)) {
            whereBuild.append("AND eva.semester_id = :semesterId ");
            params.put("semesterId", semesterId);
        }

        if (Objects.nonNull(status)) {
            whereBuild.append("AND eva.final_evaluation_status = :status ");
            params.put("status", status);
        }

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query countQuery = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);

        params.forEach(countQuery::setParameter);

        List<FinalEvaluationDTO> total = countQuery.getResultList();

        long totalEntities = total.size();
        long totalPage = (long) Math.ceil((double) totalEntities / pageable.getPageSize());

        queryBuild = queryBuild.concat("LIMIT :offset, :limit");

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING)
                .setParameter("offset", pageable.getPageNumber())
                .setParameter("limit", pageable.getPageSize());

        params.forEach(query::setParameter);

        List<FinalEvaluationDTO> data = query.getResultList();

        return PageResponse.success(data, pageable.getPageNumber(), pageable.getPageSize(), totalEntities, totalPage);
    }

    @Override
    public BaseResponse<FinalEvaluationResponse> get(Long id) {
        try {
            FinalEvaluation finalEvaluation = finalEvaluationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy đánh giá cuối kỳ với id %s", id)));
            return BaseResponse.success(FinalEvaluationResponse.of(finalEvaluation));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<FinalEvaluationResponse> save(FinalEvaluationRequest request) {
        try {
            if (request.getStartDate().after(request.getEndDate())) {
                throw new IllegalArgumentException("StartDate và EndDate không hợp lệ");
            }

            FinalEvaluation finalEvaluation = finalEvaluationRepository.findByStaffCode(request.getStaffCode());

            if (Objects.nonNull(finalEvaluation)) {
                throw new IllegalArgumentException("Staff code đã tồn tại!");
            }

            return Objects.isNull(request.getId()) ? create(request) : update(request);

        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<FinalEvaluationResponse> create(FinalEvaluationRequest request) {

        FinalEvaluation finalEvaluation = FinalEvaluation.of(request);

        finalEvaluation.setCreatedAt(DateUtils.now());
        finalEvaluation.setCreatedBy(request.getContactId());

        finalEvaluation = finalEvaluationRepository.save(finalEvaluation);

        return BaseResponse.success(FinalEvaluationResponse.of(finalEvaluation));
    }

    @Override
    public BaseResponse<FinalEvaluationResponse> update(FinalEvaluationRequest request) {
        try {
            Long id = request.getId();
            FinalEvaluation finalEvaluation = finalEvaluationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy đánh giá cuối kỳ với id %s", id)));

            finalEvaluation.setComment(request.getComment());
            finalEvaluation.setDivision(request.getDivision());
            finalEvaluation.setDuration(request.getDuration());
            finalEvaluation.setStartDate(request.getStartDate());
            finalEvaluation.setEndDate(request.getEndDate());
            finalEvaluation.setStaffCode(request.getStaffCode());
            finalEvaluation.setAllowance(request.getAllowance());
            finalEvaluation.setMajorPoint(request.getMajorPoint());
            finalEvaluation.setAttitudePoint(request.getAttitudePoint());
            finalEvaluation.setSoftSkillPoint(request.getSoftSkillPoint());
            finalEvaluation.setFinalPoint(request.getFinalPoint());
            finalEvaluation.setModifiedAt(DateUtils.now());
            finalEvaluation.setModifiedBy(request.getContactId());

            finalEvaluation = finalEvaluationRepository.save(finalEvaluation);

            return BaseResponse.success(FinalEvaluationResponse.of(finalEvaluation));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

}
