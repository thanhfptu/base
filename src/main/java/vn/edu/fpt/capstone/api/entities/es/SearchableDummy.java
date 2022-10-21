/* TODO: Mở lại nếu dùng Elastic Search
package vn.edu.fpt.capstone.api.entities.es;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "dummies")
public class SearchableDummy {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    public static SearchableDummy of(Long id, String name) {
        return SearchableDummy.builder()
                .id(id)
                .name(name)
                .build();
    }

}
*/
