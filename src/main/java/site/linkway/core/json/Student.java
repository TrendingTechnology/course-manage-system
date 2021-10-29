package site.linkway.core.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    public String stuId;
    public String name;
    public String sex;
    public String age;
    public String department;
    public String major;
    public String password;
}
