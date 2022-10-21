package vn.edu.fpt.capstone.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = CVPreview.TABLE_NAME)
public class CVPreview extends BaseEntity{
    public static final String TABLE_NAME = "cv_preview";
    @Column(name = "cv_id")
    private Long cvId;
    @Column(name = "img_url")
    private String imgUrl;

}
