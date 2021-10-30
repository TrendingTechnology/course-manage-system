package site.linkway.core.dao;

import org.apache.hadoop.hbase.client.Result;

import java.util.ArrayList;
import java.util.List;

public class SCDao {
    public static class SC{
        @Override
        public String toString() {
            return "SC{" +
                    "courseId='" + courseId + '\'' +
                    ", studentId='" + studentId + '\'' +
                    ", score='" + score + '\'' +
                    '}';
        }

        public String courseId;
        public String studentId;
        public String score;
    }

    public static final String TABLENAME=HbaseUtil.tableNames[2];
    public static boolean add(SC sc,String score,boolean flag)  {//flag update:false  add:true
        boolean result=false;
        try{
            if(null!=selectSCById(sc).courseId&&flag){//exist and it's add ,it's not update
                return false;
            }
            if(flag==false&&null==selectSCById(sc).courseId){//it's update ,but it's not exist
                return false;
            }
            System.out.println("execute insert");
            HbaseUtil.addRecord(TABLENAME, sc.courseId+"-"+sc.studentId, HbaseUtil.scStruct,new String[]{
                    sc.courseId,
                    sc.studentId,
                    sc.score
            });
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static SC selectSCById(SC sc){
        SC back=new SC();
        try{
            System.out.println(sc.courseId+"-"+sc.studentId);
            Result result=HbaseUtil.getData(TABLENAME, sc.courseId+"-"+sc.studentId);
            if(null!=result){
                back.courseId= new String(result.getValue("CourseID".getBytes(),"id".getBytes()));
                back.studentId=new String(result.getValue("StudentID".getBytes(),"id".getBytes()));
                back.score=new String(result.getValue("Score".getBytes(),"score".getBytes()));
                System.out.println("exist SC");
                System.out.println(back);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }

    public static boolean update(SC sc){
        boolean result=false;
        try{
            if(null==selectSCById(sc).courseId){//no exist
                System.out.println("update filed no exist");
                return false;
            }
            System.out.println("execute update");
            HbaseUtil.addRecord(TABLENAME, sc.courseId+"-"+sc.studentId, HbaseUtil.scStruct,new String[]{
                    sc.courseId,
                    sc.courseId,
                    sc.score
            });
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static boolean delete(SC sc){
        boolean result=false;
        try{
            if(null==selectSCById(sc).courseId){//no exist
                System.out.println("delete filed no exist");
                return false;
            }
            System.out.println("execute delete");
            HbaseUtil.deleteRow(TABLENAME,sc.courseId+"-"+sc.studentId);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static List<SC> scListFromResultList(List<Result> results){
        List<SC> courses=new ArrayList<>();
        for(Result result:results){
            if(null!=result){
                SC back=new SC();
                back.courseId= new String(result.getValue("CourseID".getBytes(),"id".getBytes()));
                back.studentId=new String(result.getValue("StudentID".getBytes(),"id".getBytes()));
                back.score=new String(result.getValue("Score".getBytes(),"score".getBytes()));
                courses.add(back);
            }
        }
        return courses;
    }


    /*
     * Dao Testing
     * */
    public static void main(String[] args) {
        SC sc=new SC();
        sc.studentId="cdcdf";
        sc.courseId="fvf";
        sc.score="43";
        if(add(sc,"0",true)){
            System.out.println("insert sucuss");
        }else{
            System.out.println("insert failed");
        }

        //update
        sc.score="4343";
        update(sc);

        //delete
        //delete(sc);

        //select
        SC sc1=new SC();
        sc1.courseId="fvf";
        sc1.studentId="cdcdf";
        SC sc2=selectSCById(sc1);
        System.out.println(sc2);

        //selectall
        System.out.println("SELECT ALL");
        try{
            List<SC> scs=scListFromResultList(HbaseUtil.scan(TABLENAME));
            for(SC sc3:scs){
                System.out.println(sc3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
