package site.linkway.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.linkway.core.dao.CourseDao;
import site.linkway.core.dao.HbaseUtil;
import site.linkway.core.dao.SCDao;
import site.linkway.core.dao.UserDao;
import site.linkway.core.json.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private ObjectMapper mapper=new ObjectMapper();

    @RequestMapping(value = "/addStudent",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addStudent(@NonNull String id,
                        @NonNull String password,
                        @NonNull String sex,
                        @NonNull String name,
                        @NonNull int age,
                        @NonNull String department,
                        @NonNull String major,
                        HttpSession httpSession) throws Exception{
        UserDao.User user=new UserDao.User();
        user.password=password;user.stuId=id;user.sex=sex;user.department=department;
        user.age=Integer.toString(age);user.name=name;user.major=major;
        boolean result= UserDao.addStudent(user);
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

    @RequestMapping(value = "/addCourse",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addCourse(@NonNull  String courseId,
                            @NonNull  String name,
                            @NonNull  String credit,
                            @NonNull   String time,
                            @NonNull  String teacher,
                            @NonNull  String title) throws Exception{
        CourseDao.Course course=new CourseDao.Course();
        course.courseId=courseId;
        course.name=name;
        course.credit=credit;
        course.time=time;
        course.teacher=teacher;
        course.title=title;
        boolean result= CourseDao.add(course);
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

    @RequestMapping(value = "/deleteCourse",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteCourse(@NonNull String courseId)throws Exception{
        CourseDao.Course course=new CourseDao.Course();
        course.courseId=courseId;
        boolean result= CourseDao.delete(course);
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

    @RequestMapping(value = "/deleteStudent",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteStudent(@NonNull String id)throws Exception{
        UserDao.User user=new UserDao.User();
        user.stuId=id;
        boolean result= UserDao.delete(user);
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

    @RequestMapping(value = "/getStudentList",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getStudentList()throws Exception{
        List<UserDao.User> userList =UserDao.userListFromResultList(HbaseUtil.scan("Student"));
        AllStudent allStudent=new AllStudent();
        allStudent.students=new ArrayList<>();
        for(UserDao.User user:userList){
            Student user1=new Student();
            user1.stuId=user.stuId;
            user1.name=user.name;
            user1.age=user.age;
            user1.sex=user.sex;
            user1.major=user.major;
            user1.department=user.department;
            user1.password=user.password;
            allStudent.students.add(user1);
        }
        return mapper.writeValueAsString(allStudent);
    }

    @RequestMapping(value = "/updateSC",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateSC(@NonNull String courseId,@NonNull String  userid,@NonNull int score)throws Exception{
        //add
        SCDao.SC sc=new SCDao.SC();
        sc.studentId=userid;
        sc.courseId=courseId;
        sc.score=Integer.toString(score);
        boolean result=SCDao.add(sc,Integer.toString(score));
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);
        statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

    @RequestMapping(value="/getSCList",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getSCList()throws Exception{
        List<SCDao.SC>scs=SCDao.scListFromResultList(HbaseUtil.scan("SC"));
        AllSC allSC=new AllSC();
        allSC.scs=new ArrayList<>();
        for(SCDao.SC sc:scs){
            SC sc1=new SC();
            sc1.courseId=sc.courseId;
            sc1.studentId=sc.studentId;
            sc1.score=sc.score;
            allSC.scs.add(sc1);
        }
        return mapper.writeValueAsString(allSC);
    }


}
