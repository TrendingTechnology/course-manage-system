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
import site.linkway.core.json.AllCourse;
import site.linkway.core.json.Course;
import site.linkway.core.json.StatusResult;
import site.linkway.core.json.Student;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/logined")
public class ChooseCourseController {
    private ObjectMapper mapper=new ObjectMapper();

    @RequestMapping(value = "/getCourseList",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCourseList() throws Exception{
        List<CourseDao.Course> courseList=CourseDao.courseListFromResultList(HbaseUtil.scan("Course"));
        AllCourse allCourse=new AllCourse();
        allCourse.courses=new ArrayList<>();
        for(CourseDao.Course course:courseList){
            Course course1=new Course();
            course1.courseId=course.courseId;
            course1.name=course.name;
            course1.credit=course.credit;
            course1.teacher=course.teacher;
            course1.time=course.time;
            course1.title=course.title;
            allCourse.courses.add(course1);
        }
        allCourse.status=200;
        return mapper.writeValueAsString(allCourse);
    }

    @RequestMapping(value = "/chooseCourse",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String chooseCourse(@NonNull String courseId,HttpSession httpSession)throws Exception{
        //check course exist
        System.out.println(courseId);
        StatusResult statusResult=new StatusResult();
        CourseDao.Course course=new CourseDao.Course();
        course.courseId=courseId;
        course=CourseDao.selectCourseById(course);
        if(course.courseId==null){
            //no exist
            statusResult.setStatus(200);
            statusResult.setResult(false);
            System.out.println("no exist");
            return mapper.writeValueAsString(statusResult);
        }
        //add
        SCDao.SC sc=new SCDao.SC();
        sc.studentId=(String)httpSession.getAttribute("id");
        sc.courseId=courseId;
        sc.score="0";
        boolean result=SCDao.add(sc,"0",true);
        statusResult.setStatus(200);
        statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

    //get my sc
    @RequestMapping(value = "/getMyCourse",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getMyCourse(HttpSession httpSession)throws Exception{
        HashMap<String,String>map=new HashMap<>();
        List<SCDao.SC> scs=SCDao.scListFromResultList(HbaseUtil.scan("SC"));
        //create map
        for(SCDao.SC sc:scs){
            System.out.println(sc);
            map.put(sc.courseId+"-"+sc.studentId,sc.score);
        }
        List<CourseDao.Course> courseList=CourseDao.courseListFromResultList(HbaseUtil.scan("Course"));
        AllCourse allCourse=new AllCourse();
        allCourse.courses=new ArrayList<>();
        String userid=(String)httpSession.getAttribute("id");
        for(CourseDao.Course course:courseList){
            System.out.println(map.get(course.courseId+"-"+userid));
            if(null!=map.get(course.courseId+"-"+userid)) {
                Course course1 = new Course();
                course1.courseId = course.courseId;
                course1.name = course.name;
                course1.credit = course.credit;
                course1.teacher = course.teacher;
                course1.time = course.time;
                course1.title = course.title;
                course1.score=map.get(course.courseId+"-"+userid);
                allCourse.courses.add(course1);
            }
        }
        allCourse.status=200;
        return mapper.writeValueAsString(allCourse);
    }

    //delete sc
    @RequestMapping(value = "/deleteMyCourse",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteMyCourse(@NonNull String courseId,HttpSession httpSession) throws Exception{
        SCDao.SC sc=new SCDao.SC();
        sc.courseId=courseId;
        sc.studentId=(String)httpSession.getAttribute("id");
        boolean result=SCDao.delete(sc);
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);
        statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

    //get self data
    @RequestMapping(value = "/getMyData",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getMyData(HttpSession httpSession) throws Exception{
        UserDao.User user=new UserDao.User();
        user.stuId=(String)httpSession.getAttribute("id");
        user=UserDao.selectUserById(user);
        Student student=new Student();
        student.age=user.age;
        student.stuId=user.stuId;
        student.name=user.name;
        student.password=user.password;
        student.sex=user.sex;
        student.department=user.department;
        student.major=user.major;
        return mapper.writeValueAsString(student);
    }

}
