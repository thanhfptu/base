package vn.edu.fpt.capstone.api.services.midtermevaluation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.EvaluationComment;
import vn.edu.fpt.capstone.api.entities.CompanyContact;
import vn.edu.fpt.capstone.api.entities.MidtermEvaluation;
import vn.edu.fpt.capstone.api.entities.Semester;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.dto.MidtermEvaluationDTO;
import vn.edu.fpt.capstone.api.repositories.CompanyContactRepository;
import vn.edu.fpt.capstone.api.repositories.MidtermEvaluationRepository;
import vn.edu.fpt.capstone.api.repositories.SemesterRepository;
import vn.edu.fpt.capstone.api.repositories.UserRepository;
import vn.edu.fpt.capstone.api.requests.MidtermEvaluationRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static vn.edu.fpt.capstone.api.entities.dto.MidtermEvaluationDTO.SQL_RESULT_SET_MAPPING;

@Slf4j
@RequiredArgsConstructor
@Service(MidtermEvaluationService.SERVICE_NAME)
public class MidtermEvaluationServiceImpl implements MidtermEvaluationService {

    private final EntityManager entityManager;

    private final MidtermEvaluationRepository midtermEvaluationRepository;

    @Override
    public PageResponse<MidtermEvaluationDTO> list(Pageable pageable,
                                                   String studentName,
                                                   String rollNumber,
                                                   Long companyId,
                                                   Long semesterId,
                                                   String companyName,
                                                   Date startDate,
                                                   Date endDate,
                                                   String staffCode,
                                                   Integer comment,
                                                   Long companyContactId,
                                                   String contactName,
                                                   Integer status) {

        StringBuilder selectBuild = new StringBuilder("SELECT ")
                .append("midterm.id as id, ")
                .append("semester.name as semester, ")
                .append("company.name as company_name, ")
                .append("midterm.start_date as start_date, ")
                .append("midterm.end_date as end_date, ")
                .append("midterm.duration as duration, ")
                .append("student.roll_number as roll_number, ")
                .append("midterm.staff_code as staff_code, ")
                .append("student.full_name as full_name, ")
                .append("midterm.division as division, ")
                .append("contact.name as contact_name, ")
                .append("midterm.comment as comment, ")
                .append("midterm.note as note, ")
                .append("eva.midterm_evaluation_status as status ");

        StringBuilder fromBuild = new StringBuilder("FROM ")
                .append("midterm_evaluation midterm ")
                .append("RIGHT JOIN evaluation eva on eva.midterm_evaluation_id = midterm.id ")
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
            whereBuild.append("AND eva.staff_code like CONCAT('%',:staffCode,'%') ");
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
            whereBuild.append("AND eva.start_date = :startDate ");
            params.put("startDate", startDate);
        }

        if (Objects.nonNull(endDate)) {
            whereBuild.append("AND eva.end_date = :endDate ");
            params.put("endDate", endDate);
        }

        if (Objects.nonNull(comment)) {
            whereBuild.append("AND eva.comment = :comment ");
            params.put("comment", comment);
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
            whereBuild.append("AND eva.midterm_evaluation_status = :status ");
            params.put("status", status);
        }

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query countQuery = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);

        params.forEach(countQuery::setParameter);

        List<MidtermEvaluationDTO> total = countQuery.getResultList();

        long totalEntities = total.size();
        long totalPage = (long) Math.ceil((double) totalEntities / pageable.getPageSize());

        queryBuild = queryBuild.concat("LIMIT :offset, :limit");

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING)
                .setParameter("offset", pageable.getPageNumber())
                .setParameter("limit", pageable.getPageSize());

        params.forEach(query::setParameter);

        List<MidtermEvaluationDTO> data = query.getResultList();

        return PageResponse.success(data, pageable.getPageNumber(), pageable.getPageSize(), totalEntities, totalPage);
    }

    @Override
    public BaseResponse<MidtermEvaluationResponse> get(Long id) {
        try {
            MidtermEvaluation midtermEvaluation = midtermEvaluationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy đánh giá giữa kỳ với id %s", id)));

            return BaseResponse.success(MidtermEvaluationResponse.of(midtermEvaluation));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<MidtermEvaluationResponse> save(MidtermEvaluationRequest request) {
        try {
            if(request.getStartDate().after(request.getEndDate())) {
                throw new IllegalArgumentException("StartDate và EndDate không hợp lệ");
            }

            Long id = request.getId();


            MidtermEvaluation midtermEvaluation = midtermEvaluationRepository.findByStaffCode(request.getStaffCode())
                    .orElse(null);

            if (Objects.nonNull(midtermEvaluation)) {
                throw new IllegalArgumentException("Staff code đã tồn tại!");
            }

            return Objects.isNull(id) ? create(request) : update(request);

        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }
        @Override
        public BaseResponse<MidtermEvaluationResponse> create (MidtermEvaluationRequest request){

            MidtermEvaluation midtermEvaluation = MidtermEvaluation.of(request);

            midtermEvaluation.setCreatedAt(DateUtils.now());
            midtermEvaluation.setCreatedBy(request.getContactId());

            midtermEvaluation = midtermEvaluationRepository.save(midtermEvaluation);

            return BaseResponse.success(MidtermEvaluationResponse.of(midtermEvaluation));

        }

        @Override
        public BaseResponse<MidtermEvaluationResponse> update (MidtermEvaluationRequest request){
            try {
                Long id = request.getId();
                MidtermEvaluation midtermEvaluation = midtermEvaluationRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy đánh giá giữa kỳ với id %s", id)));

                midtermEvaluation.setComment(EvaluationComment.of(request.getComment()));
                midtermEvaluation.setDivision(request.getDivision());
                midtermEvaluation.setDuration(request.getDuration());
                midtermEvaluation.setStartDate(request.getStartDate());
                midtermEvaluation.setEndDate(request.getEndDate());
                midtermEvaluation.setNote(request.getNote());
                midtermEvaluation.setStaffCode(request.getStaffCode());
                midtermEvaluation.setModifiedAt(DateUtils.now());
                midtermEvaluation.setModifiedBy(request.getContactId());

                midtermEvaluation = midtermEvaluationRepository.save(midtermEvaluation);

                return BaseResponse.success(MidtermEvaluationResponse.of(midtermEvaluation));
            } catch (IllegalArgumentException e) {
                return BaseResponse.error(e.getMessage());
            }
        }

    }
