package site.linkway.core.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SC {
    public String courseId;
    public String studentId;
    public String score;
}
