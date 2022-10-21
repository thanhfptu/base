package vn.edu.fpt.capstone.api.entities.dto;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name = AppliedJobDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = AppliedJobDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "title", type = String.class),
                }))
public class AppliedJobDTO {
    public static final String SQL_RESULT_SET_MAPPING = "AppliedJobDTO";
    @Id
    private Long id;
    private String title;
}
