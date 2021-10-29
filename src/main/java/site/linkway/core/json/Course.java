package site.linkway.core.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    public String courseId;
    public String name;
    public String credit;
    public String time;
    public String teacher;
    public String title;
}
