package site.linkway.core.dao;

import org.apache.hadoop.hbase.client.Result;

import java.util.ArrayList;
import java.util.List;

/*
*
* */
public class CourseDao {
    public static class Course{
        @Override
        public String toString() {
            return "Course{" +
                    "courseId='" + courseId + '\'' +
                    ", name='" + name + '\'' +
                    ", credit='" + credit + '\'' +
                    ", time='" + time + '\'' +
                    ", teacher='" + teacher + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        public String courseId;
        public String name;
        public String credit;
        public String time;
        public String teacher;
        public String title;
    }

    public static final String TABLENAME=HbaseUtil.tableNames[1];
    public static boolean add(Course course)  {
        boolean result=false;
        try{
            if(null!=selectCourseById(course).courseId){//exist
                System.out.println("exist");
                return false;
            }
            System.out.println("execute insert");
            HbaseUtil.addRecord(TABLENAME, course.courseId, HbaseUtil.courseStruct,new String[]{
                    course.courseId,
                    course.name,
                    course.credit,
                    course.time,
                    course.teacher,
                    course.title
            });
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }
    public static Course selectCourseById(Course course){
        Course back=new Course();
        try{
            Result result=HbaseUtil.getData(TABLENAME,course.courseId);
            if(null!=result){
                back.courseId= new String(result.getValue("CInfo".getBytes(),"id".getBytes()));
                back.name=new String(result.getValue("CInfo".getBytes(),"name".getBytes()));
                back.credit=new String(result.getValue("CInfo".getBytes(),"credit".getBytes()));
                back.time=new String(result.getValue("CInfo".getBytes(),"time".getBytes()));
                back.teacher=new String(result.getValue("Teaching".getBytes(),"teacher".getBytes()));
                back.title=new String(result.getValue("Teaching".getBytes(),"title".getBytes()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }

    public static boolean update(Course course){
        boolean result=false;
        try{
            if(null==selectCourseById(course).courseId){//no exist
                System.out.println("update filed no exist");
                return false;
            }
            System.out.println("execute update");
            HbaseUtil.addRecord(TABLENAME, course.courseId, HbaseUtil.courseStruct,new String[]{
                    course.courseId,
                    course.name,
                    course.credit,
                    course.time,
                    course.teacher,
                    course.title
            });
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static boolean delete(Course course){
        boolean result=false;
        try{
            if(null==selectCourseById(course).courseId){//no exist
                System.out.println("delete filed no exist");
                return false;
            }
            System.out.println("execute delete");
            HbaseUtil.deleteRow(TABLENAME,course.courseId);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static List<Course> courseListFromResultList(List<Result> results){
        List<Course> courses=new ArrayList<>();
        for(Result result:results){
            if(null!=result){
                Course back=new Course();
                back.courseId= new String(result.getValue("CInfo".getBytes(),"id".getBytes()));
                back.name=new String(result.getValue("CInfo".getBytes(),"name".getBytes()));
                back.credit=new String(result.getValue("CInfo".getBytes(),"credit".getBytes()));
                back.time=new String(result.getValue("CInfo".getBytes(),"time".getBytes()));
                back.teacher=new String(result.getValue("Teaching".getBytes(),"teacher".getBytes()));
                back.title=new String(result.getValue("Teaching".getBytes(),"title".getBytes()));
                courses.add(back);
            }
        }
        return courses;
    }


    /*
     * Dao Testing
     * */
    public static void main(String[] args) {
        Course course=new Course();
        course.courseId="2021-10-29-56";
        course.teacher="opp";
        course.title="dwedw";
        course.credit="benke";
        course.name="network";
        course.time="2021/10/29 - 2022/01/23";
        if(add(course)){
            System.out.println("insert sucuss");
        }else{
            System.out.println("insert failed");
        }

        //update
        course.teacher="lihua";
        update(course);

        //delete
        //delete(course);

        //select
        Course course1=new Course();
        course1.courseId="2021-10-29-56";
        Course course2=selectCourseById(course1);
        System.out.println(course2);

        //selectall
        System.out.println("SELECT ALL");
        try{
            List<Course> courses=courseListFromResultList(HbaseUtil.scan(TABLENAME));
            for(Course course3:courses){
                System.out.println(course3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
